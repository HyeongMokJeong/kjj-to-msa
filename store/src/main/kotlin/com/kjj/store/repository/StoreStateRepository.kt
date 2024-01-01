package com.kjj.store.repository

import com.kjj.store.entity.StoreState
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface StoreStateRepository: JpaRepository<StoreState, Long> {
    fun findByDate(date: LocalDate): StoreState?
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<StoreState>
}