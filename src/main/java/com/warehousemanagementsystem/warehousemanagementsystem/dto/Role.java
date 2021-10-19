package com.warehousemanagementsystem.warehousemanagementsystem.dto;

public class Role {
    String roleid;
    String name;

    public Role(String roleid, String name) {
        this.roleid = roleid;
        this.name = name;
    }

    public Role() {
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
