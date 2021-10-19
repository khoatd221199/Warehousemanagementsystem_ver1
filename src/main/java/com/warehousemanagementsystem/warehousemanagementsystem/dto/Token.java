package com.warehousemanagementsystem.warehousemanagementsystem.dto;

public class Token {
    private String token;
    private String role;

    public Token() {
    }

    public Token(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
