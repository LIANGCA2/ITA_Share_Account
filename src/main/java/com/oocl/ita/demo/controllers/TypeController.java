package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.entites.Type;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.po.MonthOfBill;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.services.TypeService;
import com.oocl.ita.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/type")
public class TypeController {
    private final TypeService typeService;
    private final LoginService loginService;
    private final UserService userService;

    @Autowired
    public TypeController(TypeService typeService, LoginService loginService, UserService userService) {
        this.typeService = typeService;
        this.loginService = loginService;
        this.userService = userService;
    }

    @GetMapping(path = "/income")
    public List<Type> getAllIncomeTypes(){
        return typeService.getAllTypes("income");
    }

    @GetMapping(path = "/outlay")
    public List<Type> getAllOutlayTypes(){
        return typeService.getAllTypes("outlay");
    }

    @GetMapping(path = "")
    public ResponseEntity<List<String>> getAllTypes(@RequestParam String trd_session) {
        HttpStatus httpStatus = HttpStatus.OK;
        String openId = loginService.getOpenId(trd_session);
        User user = userService.findUserByUserId(openId);
        if(user == null){
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
        List<String> types = typeService.getAllTypes(null).stream().map(Type::getType).collect(Collectors.toList());
        return ResponseEntity.ok(types);
    }

    @PostMapping(path = "")
    public ResponseEntity addType(@RequestBody Type type) {
        typeService.save(type);
        return ResponseEntity.ok().build();
    }
}
