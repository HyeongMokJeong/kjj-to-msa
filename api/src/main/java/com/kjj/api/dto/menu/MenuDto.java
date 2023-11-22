package com.kjj.api.dto.menu;

import com.kjj.api.entity.menu.Menu;
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
