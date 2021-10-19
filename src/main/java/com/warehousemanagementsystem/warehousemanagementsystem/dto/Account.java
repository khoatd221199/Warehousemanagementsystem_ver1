package com.warehousemanagementsystem.warehousemanagementsystem.dto;

public class Account {
    private String username;
    private String fullname;
    private String password;
    private String roleid;
    private String rolename;
    private String warehouseid;


    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String fullname, String password, String roleid, String rolename, String warehouseid) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.roleid = roleid;
        this.rolename = rolename;
        this.warehouseid = warehouseid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }
}
