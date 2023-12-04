package com.kjj.calculate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CalculateMenuForGraphDto {
    private String name;
    private long count;

    public static CalculateMenuForGraphDto from(String name, Integer count) {
        return new CalculateMenuForGraphDto(
                name,
                count
        );
    }
}
