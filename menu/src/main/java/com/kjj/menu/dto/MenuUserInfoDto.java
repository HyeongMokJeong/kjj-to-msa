package com.kjj.menu.dto;

import com.kjj.menu.entity.Menu;
import com.kjj.menu.entity.MenuFood;
import com.kjj.menu.entity.MenuInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuUserInfoDto {
    private Long id;
    private String name;
    private int cost;
    private String image;
    private Boolean sold;
    private Long foodId;

    private String info;
    private String details;

    public static MenuUserInfoDto from(Menu menu) {
        MenuFood menuFood = menu.getMenuFood();
        MenuInfo menuInfo = menu.getMenuInfo();
        return new MenuUserInfoDto(
                menu.getId(),
                menu.getName(),
                menu.getCost(),
                menu.getImage(),
                menu.getSold(),
                (menuFood == null) ? null : menuFood.getId(),
                (menuInfo == null) ? null : menuInfo.getInfo(),
                (menuInfo == null) ? null : menuInfo.getDetails()
        );
    }
}
