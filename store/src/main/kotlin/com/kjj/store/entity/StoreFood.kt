package com.kjj.store.entity

import jakarta.persistence.*

@Entity
class StoreFood(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY)
    val store: Store,
    val food: String
) {
}