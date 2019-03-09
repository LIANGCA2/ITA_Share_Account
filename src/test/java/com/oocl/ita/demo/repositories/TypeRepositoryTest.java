package com.oocl.ita.demo.repositories;

import com.oocl.ita.demo.entites.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.*;

@RunWith(value = Parameterized.class)
public class TypeRepositoryTest {
    @Autowired
    private TypeRepository repository;



    @Test
    public void testInsertType() {
        Type type  = new Type();
        type.setType("餐饮");
        type.setAccountKind("outcome");
        this.repository.save(type);
    }


}