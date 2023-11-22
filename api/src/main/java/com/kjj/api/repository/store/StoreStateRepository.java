package com.kjj.api.repository.store;

import com.kjj.api.entity.store.StoreState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StoreStateRepository extends JpaRepository<StoreState, Long> {
    Optional<StoreState> findByDate(LocalDate date);

    List<StoreState> findAllByDateBetween(LocalDate start, LocalDate end);
}
