package com.kjj.menu.entity;

import com.kjj.menu.dto.MenuUpdateDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
