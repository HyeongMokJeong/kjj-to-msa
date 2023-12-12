package com.kjj.store.dto;

import com.kjj.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreDto {
    private String name;
    private String info;
    private String image;

    public static StoreDto from(Store store) {
        return new StoreDto(
                store.getName(),
                store.getInfo(),
                store.getImage()
        );
    }
}