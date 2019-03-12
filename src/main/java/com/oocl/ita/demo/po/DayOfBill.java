package com.oocl.ita.demo.po;

import java.util.*;

public class DayOfBill {

    private String date;
    private Double income;
    private Double outlay;
    private List<Record> records;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getOutlay() {
        return outlay;
    }

    public void setOutlay(Double outlay) {
        this.outlay = outlay;
    }

    public List<Record> getRecords() {
        if (this.records == null) {
            this.records = new ArrayList<Record>();
        }
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
