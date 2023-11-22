package com.kjj.api.dto.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjj.api.entity.menu.MenuFood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuFoodDto {
    private Long id;
    private String name;
    private HashMap<String, Integer> food;

    public static MenuFoodDto from(MenuFood menuFood) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Integer> food;
        try {
            food = objectMapper.readValue(menuFood.getFood(), HashMap.class);
        } catch (JsonProcessingException e) {
            food = null;
        }
        return new MenuFoodDto(
                menuFood.getId(),
                menuFood.getName(),
                food
        );
    }
}
