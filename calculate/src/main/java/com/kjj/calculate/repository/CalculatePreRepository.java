package com.kjj.calculate.repository;

import com.kjj.calculate.entity.CalculatePre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculatePreRepository extends JpaRepository<CalculatePre, Long> {
    CalculatePre findTopByOrderByIdDesc();
}
