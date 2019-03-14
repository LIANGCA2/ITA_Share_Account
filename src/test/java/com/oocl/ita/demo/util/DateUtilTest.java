package com.oocl.ita.demo.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilTest {

    @Test
    public void should_return_right_date_when_call_getFirstDateInMonth_given_correct_time() {
        //Given
        String time1 = "2019-01-20";
        String time2 = "";

        //When
        Date result1 = DateUtil.getFirstDateInMonth(time1);
        Date result2 = DateUtil.getFirstDateInMonth(time2);

        //Then
        Assert.assertNotNull(result1);
        Assert.assertNull(result2);
    }

    @Test
    public void should_return_right_date_when_call_getLastDateInMonth_given_correct_time() {
        //Given
        String time1 = "2019-09-10";
        String time2 = "";

        //When
        Date result1 = DateUtil.getLastDateInMonth(time1);
        Date result2 = DateUtil.getLastDateInMonth(time2);

        //Then
        Assert.assertNotNull(result1);
        Assert.assertNull(result2);
    }

    @Test
    public void should_return_right_date_when_call_getDateFromString_given_dateStr() {
        //Given
        String dateStr1 = "2019-01-20";

        //When
        Date result1 = DateUtil.getDateFromString(dateStr1);

        //Then
        Assert.assertNotNull(result1);
    }

    @Test
    public void should_return_right_date_when_call_getDateFromString_given_wrong_dateStr() {
        //Given
        String dateStr1 = "2019";

        //When
        Date result1 = DateUtil.getDateFromString(dateStr1);

        //Then
        Assert.assertNotNull(result1);
    }

}