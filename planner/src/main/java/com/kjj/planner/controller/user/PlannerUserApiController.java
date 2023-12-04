package com.kjj.planner.controller.user;

import com.kjj.planner.dto.PlannerDto;
import com.kjj.planner.exception.WrongParameterException;
import com.kjj.planner.service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/planner/user")
@RestController
public class PlannerUserApiController {

    private final PlannerService service;

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<PlannerDto> getPlannerByDay(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
        return ResponseEntity.ok(service.getPlannerByDay(year, month, day));
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<PlannerDto>> getPlannerByMonth(@PathVariable int year, @PathVariable int month) throws WrongParameterException {
        if (0 >= month || month > 12) throw new WrongParameterException("month(1 ~ 12) : " + month);
        return ResponseEntity.ok(service.getPlannerByMonth(year, month));
    }
}
