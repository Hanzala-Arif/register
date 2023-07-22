package com.example.register;

public class order {

    public order(String ordernum, String csname, String details, Boolean delievery, Boolean OK) {
        this.ordernum = ordernum;
        this.csname = csname;
        this.details = details;
        this.delievery = delievery;
        this.OK = OK;
    }


    String ordernum, csname, details;
    Boolean delievery, OK;

    public String getOrdernum() {
        return ordernum;
    }

    public String getCsname() {
        return csname;
    }

    public String getDetails() {
        return details;
    }

    public Boolean getDelievery() {
        return delievery;
    }

    public Boolean getOK() {
        return OK;
    }
}


