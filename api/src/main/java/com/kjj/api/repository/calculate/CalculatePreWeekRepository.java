package com.kjj.api.repository.calculate;

import com.kjj.api.entity.calculate.CalculatePreWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculatePreWeekRepository extends JpaRepository<CalculatePreWeek, Long> {
    CalculatePreWeek findFirstByOrderByIdDesc();
}
