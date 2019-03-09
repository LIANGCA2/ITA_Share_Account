package com.oocl.ita.demo.po;

import java.util.*;

public class MonthOfBill {
    private String date;
    private List<DayOfBill> bill;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DayOfBill> getBill() {
        if (bill == null){
           this.bill = new ArrayList<DayOfBill>();
        }
        return bill;
    }

    public void setBill(List<DayOfBill> bill) {
        this.bill = bill;
    }
}
