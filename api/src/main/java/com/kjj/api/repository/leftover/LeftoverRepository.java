package com.kjj.api.repository.leftover;

import com.kjj.api.entity.leftover.Leftover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeftoverRepository extends JpaRepository<Leftover, Long> {

    Optional<Leftover> findByLeftoverPreId(Long id);
}
