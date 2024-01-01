package com.kjj.store.dto

import com.kjj.store.entity.StoreState
import com.kjj.store.util.DateTool

data class StoreStateDto(
    val data: String,
    val name: String,
    val off: Boolean
) {
    companion object {
        fun from(state: StoreState): StoreStateDto {
            return StoreStateDto(
                DateTool.makeLocalDateToFormatterString(state.date),
                state.name,
                state.off
            )
        }
    }
}