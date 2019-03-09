package com.oocl.ita.demo.repositories;

import com.oocl.ita.demo.entites.Type;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TypeRepositoryTest {
    @Autowired
    private TypeRepository repository;


    @Test
    public void testInsertType() {
        Type type  = new Type();
        type.setType("餐饮");
        type.setAccountKind("");
    }


}