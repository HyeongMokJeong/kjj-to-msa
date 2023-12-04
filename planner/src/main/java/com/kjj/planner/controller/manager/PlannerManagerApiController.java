package com.kjj.planner.controller.manager;

import com.kjj.planner.dto.PlannerDto;
import com.kjj.planner.service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1/planner/manager")
@RestController
public class PlannerManagerApiController {

    private final PlannerService service;

    @PostMapping("/{year}/{month}/{day}")
    public ResponseEntity<PlannerDto> setPlanner(@RequestBody PlannerDto dto, @PathVariable int year, @PathVariable int month, @PathVariable int day) {
        return ResponseEntity.ok(service.setPlanner(dto, year, month, day));
    }
}
