package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.entites.Type;
import com.oocl.ita.demo.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1/type")
public class TypeController {
    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping(path = "/income")
    public List<Type> getAllIncomeTypes(){
        return typeService.getAllTypes("1");
    }

    @GetMapping(path = "/outlay")
    public List<Type> getAllOutlayTypes(){
        return typeService.getAllTypes("0");
    }
}
