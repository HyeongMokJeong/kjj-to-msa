package com.kjj.menu.dto;

import com.kjj.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuNameAndCostDto {
    private String name;
    private Integer cost;

    public static MenuNameAndCostDto from(Menu menu) {
        return new MenuNameAndCostDto(
                menu.getName(),
                menu.getCost()
        );
    }
}
