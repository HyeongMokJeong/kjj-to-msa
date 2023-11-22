package com.kjj.api.dto.store;

import com.kjj.api.service.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class StoreWeekendDto {
    private String date;
    private int count;

    public static StoreWeekendDto newZeroDataStoreWeekendDto(LocalDate targetDate) {
        return new StoreWeekendDto(
                DateUtil.makeLocaldateToFormatterString(targetDate),
                0);
    }

    public static StoreWeekendDto of(LocalDate targetDate, int today) {
        return new StoreWeekendDto(
                DateUtil.makeLocaldateToFormatterString(targetDate),
                today
        );
    }
}
