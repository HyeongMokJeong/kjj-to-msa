package com.kjj.noauth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Random;

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

    public static User ofKeycloak(String sub, String roles) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 10;

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return new User(
                null,
                sub,
                sb.toString(),
                roles
        );
    }
}
