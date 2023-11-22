package com.kjj.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
    private String roles;

    public static User from(Object[] objects) {
        return new User(
                (Long) objects[0],
                (String) objects[1],
                (String) objects[2],
                (String) objects[3]
        );
    }
}
