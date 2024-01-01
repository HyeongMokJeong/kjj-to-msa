package com.kjj.store.repository

import com.kjj.store.entity.Store
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository: JpaRepository<Store, Long> {
}