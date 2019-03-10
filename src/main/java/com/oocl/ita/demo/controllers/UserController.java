package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.services.UserService;
import com.oocl.ita.demo.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserInfo> getUserInfo(@RequestParam String trd_session) {
        HttpStatus httpStatus = HttpStatus.OK;
        String openId = loginService.getOpenId(trd_session);
        if(openId == null){
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        UserInfo userInfo = userService.getUserInfo(openId);
        return new ResponseEntity<>(userInfo, httpStatus);
    }

    @PostMapping(path = "")
    public ResponseEntity updateUserInfo(@RequestParam String trd_session, @RequestBody User user) {
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        String openId = loginService.getOpenId(trd_session);
        if(openId == null){
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        userService.updateUserInfo(openId, user);
        return new ResponseEntity(httpStatus);
    }
}
