package com.oocl.ita.demo.controllers;


import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.util.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    private final LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response wechatLogIn(@RequestParam Map<String, String> params) {
        String loginStatus = params.get("trd_session");
        Response response = new Response();
        if(loginStatus != null) {
            boolean isLogin = loginService.checkLogin(loginStatus);
            response.setMsg(isLogin ? "Login" : "noLogin");
            response.setSuccess(isLogin);
        } else {
            String code = params.get("code");
            String result = loginService.weChatLogin(code);
            boolean isSuccess = !result.startsWith("error");
            response.setSuccess(isSuccess);
            Map<String, Object> map = new HashMap<>();
            if(isSuccess){
                map.put("trd_session", result);
            }else {
                map.put("error_msg",result.substring(6));
            }
            response.setObj(map);
        }
        return response;
    }


}
