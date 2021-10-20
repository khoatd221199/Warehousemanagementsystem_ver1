package com.warehousemanagementsystem.warehousemanagementsystem.dto;

public class Store {

    private String storeid;


    private String username;


    private String name;


    private String address;


    private String status;


    private String datetimecreate;

    public Store(String storeid, String username, String name, String address, String status, String datetimecreate) {
        this.storeid = storeid;
        this.username = username;
        this.name = name;
        this.address = address;
        this.status = status;
        this.datetimecreate = datetimecreate;
    }

    public Store() {
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetimecreate() {
        return datetimecreate;
    }

    public void setDatetimecreate(String datetimecreate) {
        this.datetimecreate = datetimecreate;
    }
}
