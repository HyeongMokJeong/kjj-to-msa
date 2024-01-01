package com.kjj.store.dto

import com.kjj.store.entity.Store

data class StoreDto(
    val name: String,
    val info: String,
    val image: String?
) {
    companion object {
        fun from(store: Store): StoreDto {
            return StoreDto(
                store.name,
                store.info,
                store.image
            )
        }
    }
}