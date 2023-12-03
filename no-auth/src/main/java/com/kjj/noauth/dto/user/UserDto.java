package com.kjj.noauth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String roles;

    public static UserDto ofKeycloak(String sub, String roles) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 10;

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return new UserDto(
                null,
                sub,
                sb.toString(),
                roles
        );
    }
}
