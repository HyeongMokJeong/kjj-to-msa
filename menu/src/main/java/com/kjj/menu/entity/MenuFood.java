package com.kjj.menu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MenuFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "menuFood")
    private List<Menu> menu;

    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "\\{.*\\}")
    private String food;

    public static MenuFood of(String name, String food) {
        return new MenuFood(
                null,
                null,
                name,
                food
        );
    }
}