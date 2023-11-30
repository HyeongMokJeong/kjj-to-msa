package com.kjj.user.dto.info;

import com.kjj.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String username;
    private String loginDate;

    public static UserInfoDto from(User user) {
        return new UserInfoDto(
                user.getId(),
                user.getUsername(),
                user.getLoginDate()
        );
    }
}
