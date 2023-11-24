package com.kjj.noauth.dto.user;

import com.kjj.noauth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String username;

    public static UserInfoDto from(User user) {
        return new UserInfoDto(
                user.getId(),
                user.getUsername()
        );
    }

    public static UserInfoDto loginFail(String message) {
        return new UserInfoDto(
                null,
                message
        );
    }
}
