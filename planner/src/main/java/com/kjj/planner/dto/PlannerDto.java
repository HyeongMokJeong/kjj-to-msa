package com.kjj.planner.dto;

import com.kjj.planner.entity.Planner;
import com.kjj.planner.tool.DateUtil;
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
