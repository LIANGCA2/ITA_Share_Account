package com.oocl.ita.demo.entites;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TypeTest {

    @Test
    public void should_invoke_setMethod() {
        //Given
        Type type = new Type();

        //When
        type.setAccountKind("test");
        type.setAccountList(new ArrayList<>());
        type.setId(1);
        type.setImgUrl("test");
        type.setType("test");

        //Then
        Assert.assertNotNull(type);
    }
}