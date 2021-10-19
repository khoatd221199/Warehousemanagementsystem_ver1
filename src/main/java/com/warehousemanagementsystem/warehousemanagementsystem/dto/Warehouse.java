package com.warehousemanagementsystem.warehousemanagementsystem.dto;

public class Warehouse {
    String warehouseid;
    String name;
    String storeid;
    String address;
    String status;
    String datetimecreate;

    public Warehouse() {
    }

    public Warehouse(String warehouseid, String name, String address) {
        this.warehouseid = warehouseid;
        this.name = name;
        this.address = address;
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
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
