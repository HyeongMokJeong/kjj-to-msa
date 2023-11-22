package com.kjj.api.repository.calculate;

import com.kjj.api.entity.calculate.CalculatePre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculatePreRepository extends JpaRepository<CalculatePre, Long> {
    CalculatePre findTopByOrderByIdDesc();
}
