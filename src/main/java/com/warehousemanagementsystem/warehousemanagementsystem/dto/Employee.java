package com.warehousemanagementsystem.warehousemanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Employee {

    private String username;

    private String warehousename;

    private String warehouseid;

    private String password;

    private String fullname;

    private String sex;

    private String birthday;

    private String email;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String datetimecreated;
    private String roleid;
    private String rolename;

    private String status;


    public Employee() {
    }

    public Employee(String username, String warehousename, String warehouseid, String password, String fullname, String sex, String birthday, String email, String datetimecreated, String roleid, String rolename, String status) {
        this.username = username;
        this.warehousename = warehousename;
        this.warehouseid = warehouseid;
        this.password = password;
        this.fullname = fullname;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.datetimecreated = datetimecreated;
        this.roleid = roleid;
        this.rolename = rolename;
        this.status = status;
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWarehousename() {
        return warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatetimecreated() {
        return datetimecreated;
    }

    public void setDatetimecreated(String datetimecreated) {
        this.datetimecreated = datetimecreated;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
