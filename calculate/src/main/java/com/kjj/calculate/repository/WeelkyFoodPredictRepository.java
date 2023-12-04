package com.kjj.calculate.repository;

import com.kjj.calculate.entity.WeeklyFoodPredict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeelkyFoodPredictRepository extends JpaRepository<WeeklyFoodPredict, Long> {
    WeeklyFoodPredict findFirstByOrderByIdDesc();
}
