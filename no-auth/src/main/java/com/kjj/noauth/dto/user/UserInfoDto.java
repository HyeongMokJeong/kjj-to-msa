package com.kjj.noauth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String username;

    public static UserInfoDto from(UserDto userDto) {
        return new UserInfoDto(
                userDto.getId(),
                userDto.getUsername()
        );
    }

    public static UserInfoDto loginFail(String message) {
        return new UserInfoDto(
                null,
                message
        );
    }
}
