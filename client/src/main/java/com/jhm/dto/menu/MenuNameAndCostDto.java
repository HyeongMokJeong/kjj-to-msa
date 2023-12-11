package com.jhm.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
public record MenuNameAndCostDto(
        String name,
        Integer cost
) {
}
