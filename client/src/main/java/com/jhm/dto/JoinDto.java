package com.jhm.dto;

public class JoinDto {
    private String username;
    private String password;

    public JoinDto() {
    }

    public JoinDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
