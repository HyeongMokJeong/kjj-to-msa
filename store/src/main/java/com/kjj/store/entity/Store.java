package com.kjj.store.entity;

import com.kjj.store.dto.StoreDto;
import com.kjj.store.dto.StoreSettingDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String info;
    private String image;

    public static Store of(Long id, StoreDto dto) {
        return new Store(
                id,
                dto.getName(),
                dto.getInfo(),
                dto.getImage()
        );
    }

    public static Store of(Long id, StoreSettingDto dto) {
        return new Store(
                id,
                dto.getName(),
                dto.getInfo(),
                null
        );
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public void setName(String name) { this.name = name; }
    public void setImage(String path) { image = path; }
}
