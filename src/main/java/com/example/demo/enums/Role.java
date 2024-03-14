package com.example.demo.enums;

public enum Role {
    USER,
    ADMIN;
    public String getAuthority() {
        return name();
    }

}
