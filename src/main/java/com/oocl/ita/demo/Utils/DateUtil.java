package com.oocl.ita.demo.Utils;

import java.util.*;

public class DateUtil {

    public static Date getFirstDateInMonth(String time){
        if(time.contains("-")) {
            String year = time.split("-")[0];
            String month = time.split("-")[1];
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(year),Integer.parseInt(month)-1,1);
            return calendar.getTime();
        }
        return null;
    }

    public static Date getLastDateInMonth(String time){
        if(time.contains("-")) {
            String year = time.split("-")[0];
            String month = time.split("-")[1];
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(year),Integer.parseInt(month),0);
            return calendar.getTime();
        }
        return null;
    }
}
