package com.kjj.api.repository.sbiz;

import com.kjj.api.entity.sbiz.WeeklyFoodPredict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SbizRepository extends JpaRepository<WeeklyFoodPredict, Long> {
    WeeklyFoodPredict findFirstByOrderByIdDesc();
}
