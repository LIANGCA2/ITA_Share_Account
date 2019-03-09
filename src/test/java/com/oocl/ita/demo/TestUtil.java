package com.oocl.ita.demo;

import java.util.Calendar;
import java.util.Date;

public class TestUtil {
    public static void main(String[] args) {
        String time = "2019-03";
        String year = time.split("-")[0];
        String month = time.split("-")[1];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year),Integer.parseInt(month),0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date endDate = calendar.getTime();
        System.out.println(startDate+"    "+endDate);
    }
}
