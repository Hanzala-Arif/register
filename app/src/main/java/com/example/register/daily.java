package com.example.register;

public class daily {
    String rorder,details,date;

    public daily(String rorder, String details, String date) {
        this.rorder = rorder;
        this.details = details;
        this.date = date;
    }

    public String getRorder() {
        return rorder;
    }

    public String getDetails() {
        return details;
    }

    public String getDate() {
        return date;
    }
}
