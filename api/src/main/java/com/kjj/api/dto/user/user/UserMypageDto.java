package com.kjj.api.dto.user.user;

import com.kjj.api.entity.user.UserMypage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserMypageDto {
    private Long userId;
    private int point;

    public static UserMypageDto from(UserMypage userMyPage) {
        return new UserMypageDto(
                userMyPage.getId(),
                userMyPage.getPoint()
        );
    }
}
