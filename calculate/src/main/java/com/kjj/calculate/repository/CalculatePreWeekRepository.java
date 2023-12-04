package com.kjj.calculate.repository;

import com.kjj.calculate.entity.CalculatePreWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculatePreWeekRepository extends JpaRepository<CalculatePreWeek, Long> {
    CalculatePreWeek findFirstByOrderByIdDesc();
}
