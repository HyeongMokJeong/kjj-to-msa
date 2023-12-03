package com.kjj.user.dto.info;

import com.kjj.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String username;
    private LocalDate loginDate;

    public static UserInfoDto from(User user) {
        return new UserInfoDto(
                user.getId(),
                user.getUsername(),
                user.getLoginDate()
        );
    }
}
