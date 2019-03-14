package com.oocl.ita.demo.po;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class DayOfBillTest {
    @Test
    public void should_test_DayOfBill() {
        //Given
        DayOfBill dayOfBill = new DayOfBill();
        //When
        dayOfBill.setOutlay(222.2);

        //Then
        Assert.assertEquals((Double) 222.2, dayOfBill.getOutlay());
    }

}