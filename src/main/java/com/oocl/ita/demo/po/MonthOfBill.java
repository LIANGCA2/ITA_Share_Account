package com.oocl.ita.demo.po;

import java.util.*;

public class MonthOfBill {
    private String date;
    private List<DayOfBill> bill;
    private MonthIO monthIO;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DayOfBill> getBill() {
        if (this.bill == null){
           this.bill = new ArrayList<DayOfBill>();
        }
        return this.bill;
    }

    public void setBill(List<DayOfBill> bill) {
        this.bill = bill;
    }

    public MonthIO getMonthIO() {
        if (this.monthIO == null){
            this.monthIO = new MonthIO();
        }
        return this.monthIO;
    }

    public void setMonthIO(MonthIO monthIO) {
        this.monthIO = monthIO;
    }
}
