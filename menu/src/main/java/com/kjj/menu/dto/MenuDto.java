package com.kjj.menu.dto;

import com.kjj.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuDto {
    private Long id;
    private String name;
    private int cost;
    private String image;
    private Boolean sold;

    public static MenuDto from(Menu menu) {
        return new MenuDto(
                menu.getId(),
                menu.getName(),
                menu.getCost(),
                menu.getImage(),
                menu.getSold()
        );
    }
}
