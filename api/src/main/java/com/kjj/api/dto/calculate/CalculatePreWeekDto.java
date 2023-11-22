package com.kjj.api.dto.calculate;

import com.kjj.api.entity.calculate.CalculatePreWeek;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CalculatePreWeekDto {
    private LocalDate date;
    private int monday;
    private int tuesday;
    private int wednesday;
    private int thursday;
    private int friday;

    public static CalculatePreWeekDto from(CalculatePreWeek data) {
        return new CalculatePreWeekDto(
                data.getDate(),
                data.getMonday(),
                data.getTuesday(),
                data.getWednesday(),
                data.getThursday(),
                data.getFriday()
        );
    }
}
