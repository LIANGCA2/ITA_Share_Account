package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.services.AccountService;
import com.oocl.ita.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public UserInfo getUserInfo(@PathVariable int id) {
        return userService.getUserInfo(id);
    }
}
