package com.kjj.user.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuNameAndCostDto {
    private String name;
    private Integer cost;
}
