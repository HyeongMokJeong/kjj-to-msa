package com.kjj.api.dto.leftover;

import com.kjj.api.entity.leftover.Leftover;
import com.kjj.api.service.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class LeftoverDto {

    private String date;
    private Double leftover;

    public static LeftoverDto from(Leftover leftover) {
        return new LeftoverDto(
                DateUtil.makeLocaldateToFormatterString(leftover.getLeftoverPre().getDate()),
                leftover.getData()
        );
    }

    public static LeftoverDto of(LocalDate date, double leftover) {
        return new LeftoverDto(
                DateUtil.makeLocaldateToFormatterString(date),
                leftover
        );
    }
}
