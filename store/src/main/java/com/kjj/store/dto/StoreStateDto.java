package com.kjj.store.dto;

import com.kjj.store.entity.StoreState;
import com.kjj.store.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreStateDto {
    private String date;
    private String name;
    private Boolean off;

    public static StoreStateDto from(StoreState state) {
        return new StoreStateDto(
                DateUtil.makeLocaldateToFormatterString(state.getDate()),
                state.getName(),
                state.getOff()
        );
    }
}