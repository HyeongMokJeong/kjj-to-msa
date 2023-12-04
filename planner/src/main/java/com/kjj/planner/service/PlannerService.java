package com.kjj.planner.service;

import com.kjj.planner.dto.PlannerDto;
import com.kjj.planner.entity.Planner;
import com.kjj.planner.repository.PlannerRepository;
import com.kjj.planner.tool.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannerService{

    private final PlannerRepository repository;
    private final DateUtil dateUtil;

    @Transactional
    public PlannerDto setPlanner(PlannerDto dto, int year, int month, int day) {
        LocalDate dateString = dateUtil.makeLocalDate(year, month, day);
        dto.setDate(dateString.toString());

        Planner planner = repository.findOnePlanner(dateString).orElse(null);
        if (planner == null) {
            planner = repository.save(Planner.of(dto));
        }
        else planner.setMenus(dto.getMenus());
        return PlannerDto.from(planner);
    }

    @Transactional(readOnly = true)
    public PlannerDto getPlannerByDay(int year, int month, int day) {
        LocalDate date = dateUtil.makeLocalDate(year, month, day);

        return repository.findOnePlanner(date)
                .map(PlannerDto::from)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PlannerDto> getPlannerByMonth(int year, int month) {
        LocalDate start = dateUtil.makeLocalDate(year, month, 1);
        LocalDate end = dateUtil.makeLocalDate(year, month, dateUtil.getLastDay(year, month));

        return repository.findAllByDateBetween(start, end).stream()
                .map(PlannerDto::from)
                .toList();
    }
}