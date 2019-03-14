package com.oocl.ita.demo.po;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class MonthIOTest {
    @Test
    public void should_test_MonthIO() {
        //Given
        MonthIO monthIO = new MonthIO();
        //When
        monthIO.setBalance(222.2);

        //Then
        Assert.assertEquals((Double) 222.2, monthIO.getBalance());
    }
}
