package com.kjj.store.entity

import com.kjj.store.dto.StoreDto
import com.kjj.store.dto.StoreSettingDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Store(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var name: String,
    var info: String,
    var image: String?
) {
    companion object {
        fun of(id: Long?, dto: StoreDto): Store {
            return Store(
                id,
                dto.name,
                dto.info,
                dto.image
            )
        }

        fun of(id: Long?, dto: StoreSettingDto): Store {
            return Store(
                id,
                dto.name,
                dto.info,
                null
            )
        }
    }
}