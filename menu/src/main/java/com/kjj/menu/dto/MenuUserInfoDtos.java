package com.kjj.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuUserInfoDtos {
    private List<MenuUserInfoDto> dtos;

    public static MenuUserInfoDtos from(List<MenuUserInfoDto> dtos) {
        return new MenuUserInfoDtos(dtos);
    }
}
