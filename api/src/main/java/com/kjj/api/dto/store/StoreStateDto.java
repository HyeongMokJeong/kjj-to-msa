package com.kjj.api.dto.store;

import com.kjj.api.entity.store.StoreState;
import com.kjj.api.service.DateUtil;
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