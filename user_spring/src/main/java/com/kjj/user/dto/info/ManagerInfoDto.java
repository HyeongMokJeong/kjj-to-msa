package com.kjj.user.dto.info;

import com.kjj.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerInfoDto {
    private Long id;
    private String loginId;

    public static ManagerInfoDto from(User manager) {
        return new ManagerInfoDto(
                manager.getId(),
                manager.getUsername()
        );
    }
}