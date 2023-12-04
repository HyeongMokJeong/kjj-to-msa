package com.kjj.store.repository;

import com.kjj.store.entity.StoreState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StoreStateRepository extends JpaRepository<StoreState, Long> {
    Optional<StoreState> findByDate(LocalDate date);

    List<StoreState> findAllByDateBetween(LocalDate start, LocalDate end);
}
