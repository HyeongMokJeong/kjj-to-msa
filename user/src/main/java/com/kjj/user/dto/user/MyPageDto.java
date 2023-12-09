package com.kjj.user.dto.user;

import com.kjj.user.entity.UserMypage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDto {
    private Long userId;
    private int point;

    public static MyPageDto from(UserMypage userMyPage) {
        return new MyPageDto(
                userMyPage.getId(),
                userMyPage.getPoint()
        );
    }
}
