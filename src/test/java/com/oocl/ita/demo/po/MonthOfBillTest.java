package com.oocl.ita.demo.po;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MonthOfBillTest {
    @Test
    public void should_test_setMethod() {
        //Given
        MonthOfBill monthOfBill = new MonthOfBill();

        //When
        List<DayOfBill> list = new ArrayList<>();
        monthOfBill.setBill(list);
        monthOfBill.setDate("2012-02-28");
        MonthIO monthIO = new MonthIO();
        monthOfBill.setMonthIO(monthIO);

        //Then
        Assert.assertEquals(list, monthOfBill.getBill());
        Assert.assertEquals("2012-02-28", monthOfBill.getDate());
        Assert.assertEquals(monthIO, monthOfBill.getMonthIO());
    }

    @Test
    public void should_test_getmonthio_given_null() {
        //Given
        MonthOfBill monthOfBill = new MonthOfBill();

        //When
        MonthIO monthIO = null;
        monthOfBill.setMonthIO(monthIO);

        //Then
        Assert.assertNotNull(monthOfBill.getMonthIO());
    }
}