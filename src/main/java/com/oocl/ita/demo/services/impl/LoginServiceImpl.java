package com.oocl.ita.demo.services.impl;

import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.repositories.AccountRepository;
import com.oocl.ita.demo.repositories.UserRepository;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.util.CacheUtil;
import com.oocl.ita.demo.util.RequestUtils;
import com.oocl.ita.demo.util.StatusUtil;
import net.sf.json.JSONObject;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String weChatLogin(String code) {
        String url = code2SessionUrl.replace("APPID", app_id).replace("SECRET", app_secret).replace("JSCODE", code);
        JSONObject resultJSON = JSONObject.fromObject(RequestUtils.httpRequest(url));
        if ((int) resultJSON.getLong(error_code) > 0) {
            return "error" + "#" + (String) resultJSON.get(errro_message);
        } else {
            String openId = resultJSON.getString(open_id);
            String sessionKey = resultJSON.getString(session_key);
            String unionId = resultJSON.getString(union_id);
            String loginStatus = StatusUtil.getLoginStatus(openId, sessionKey);
            List<User> users = userRepository.findByUserId(openId);
            if(users == null || users.isEmpty()) {
                // add user to db
                User user = new User();
                user.setUserId(openId);
                userRepository.save(user);
            }
            CacheUtil.Instance.put(loginStatus, openId, sessionKey);
            return loginStatus;
        }
    }

    @Override
    public boolean checkLogin(String loginStatus) {
        String value = CacheUtil.Instance.get(loginStatus);
        return StringUtils.isNotEmpty(value);
    }


}
