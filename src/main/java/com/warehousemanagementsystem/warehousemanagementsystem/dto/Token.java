package com.warehousemanagementsystem.warehousemanagementsystem.dto;

public class Token {
    private String token;
    private String roleid;

    public Token() {
    }

    public Token(String token, String roleid) {
        this.token = token;
        this.roleid = roleid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
}
