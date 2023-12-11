package com.jhm.dto.user;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String roles;

    public UserDto() {
    }

    public UserDto(Long id, String username, String password, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
