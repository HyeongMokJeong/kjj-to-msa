package com.kjj.store.entity

import com.kjj.store.dto.StoreOffDto
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(indexes = arrayOf(
        Index(name = "store_state_date_index", columnList = "date"))
)
class StoreState(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY)
    val store: Store,

    val date: LocalDate,
    var off: Boolean,
    var name: String
) {
    companion object {
        fun createNewOffStoreState(store: Store, date: LocalDate, dto: StoreOffDto): StoreState {
            return StoreState(
                null,
                store,
                date,
                dto.off,
                dto.name
            )
        }
    }

    fun setOff(dto: StoreOffDto) {
        off = dto.off
        name = dto.name
    }
}