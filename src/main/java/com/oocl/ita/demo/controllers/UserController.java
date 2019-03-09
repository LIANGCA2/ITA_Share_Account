package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.services.UserService;
import com.oocl.ita.demo.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final LoginService loginService;
    @Autowired
    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @GetMapping(path = "")
    public UserInfo getUserInfo(@RequestParam String trd_session) {
        String openId = loginService.getOpenId(trd_session);
        return userService.getUserInfo(openId);
    }
}
