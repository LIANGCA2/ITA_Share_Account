package com.oocl.ita.demo.services.impl;

import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.repositories.UserRepository;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.util.CacheUtil;
import com.oocl.ita.demo.util.RestRequestUtil;
import com.oocl.ita.demo.util.StatusUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String weChatLogin(String code) {
        String url = code2SessionUrl.replace("APPID", app_id).replace("SECRET", app_secret).replace("JSCODE", code);
        JSONObject resultJSON = JSONObject.fromObject(RestRequestUtil.get(url));
        if (resultJSON.get(error_code) != null && (int) resultJSON.getLong(error_code) > 0) {
            return "error" + "#" + resultJSON.get(errro_message);
        } else {
            String openId = resultJSON.getString(open_id);
            String sessionKey = resultJSON.getString(session_key);
//            String unionId = resultJSON.getString(union_id);  // may be used later
            String loginStatus = StatusUtil.getLoginStatus(openId, sessionKey);
            List<User> users = userRepository.findByUserId(openId);
            setDate(users);
            if(users == null || users.isEmpty()) {
                // add user to db
                User user = new User();
                user.setUserId(openId);
                user.setDate(new Date());
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

    @Override
    public String getOpenId(String loginStatus) {
        return CacheUtil.Instance.getOpenId(loginStatus);
    }

    private void setDate(List<User> users) {
        if(users == null || users.isEmpty()) return;
        Date date = new Date();
        for(User user : users) {
            if(user.getDate() == null)
                user.setDate(date);
        }
    }

}
