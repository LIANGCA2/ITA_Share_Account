package com.oocl.ita.demo.entites;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
    @Test
    public void should_invoke_setMethod() {
        //Given
        User user = new User();

        //When
        user.setId(1);
        user.setAccountList(new ArrayList<>());
        user.setDate(new Date());
        user.setGender("1");
        user.setImage("/image/food.png");
        user.setNickName("xinruzhishui");

        //Then
        Assert.assertNotNull(user);
    }
}