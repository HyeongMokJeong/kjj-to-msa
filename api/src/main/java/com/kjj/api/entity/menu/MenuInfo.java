package com.kjj.api.entity.menu;

import com.kjj.api.dto.menu.MenuUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MenuInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String info;
    private String details;

    public void patch(MenuUpdateDto dto) {
        if (dto.getInfo() != null) this.info = dto.getInfo();
        if (dto.getDetails() != null) this.details = dto.getDetails();
    }
}