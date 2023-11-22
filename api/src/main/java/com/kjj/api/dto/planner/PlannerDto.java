package com.kjj.api.dto.planner;

import com.kjj.api.entity.planner.Planner;
import com.kjj.api.service.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlannerDto {
    private String date;
    private String menus;

    public static PlannerDto from(Planner planner){
        return new PlannerDto(
                DateUtil.makeLocaldateToFormatterString(planner.getDate()),
                planner.getMenus()
        );
    }
}
