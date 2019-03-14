package com.oocl.ita.demo.entites;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {
    @Test
    public void should_test_setMethod() {
        //Given
        Account account = new Account();

        //When
        account.setAccountKind("income");
        account.setAmount(22.22);
        account.setDate(new Date());
        account.setId(1);
        account.setDateStr("test");
        account.setIsDelete("1");
        account.setRemark("test");
        Type type = new Type();
        type.setAccountKind("income");
        account.setType(type);
        account.setUser(new User());

        //Then
        Assert.assertNotNull(account);
        Assert.assertTrue(account.isIncome());
    }
}