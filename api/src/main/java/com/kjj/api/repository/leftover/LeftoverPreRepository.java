package com.kjj.api.repository.leftover;

import com.kjj.api.entity.leftover.LeftoverPre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface LeftoverPreRepository extends JpaRepository<LeftoverPre, Long> {
    Optional<LeftoverPre> findByDate(LocalDate localDate);
}
