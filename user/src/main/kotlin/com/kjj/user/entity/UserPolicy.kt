package com.kjj.user.entity

import com.kjj.user.dto.user.DatePolicyDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class UserPolicy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var monday: Boolean,
    var tuesday: Boolean,
    var wednesday: Boolean,
    var thursday: Boolean,
    var friday: Boolean,
    var defaultMenu: Long?
) {
    companion object {
        fun createNewUserPolicy(): UserPolicy {
            return UserPolicy(
                null,
                false,
                false,
                false,
                false,
                false,
                null
            )
        }
    }

    fun setPolicy(dto: DatePolicyDto) {
        monday = dto.monday
        tuesday = dto.tuesday
        wednesday = dto.wednesday
        thursday = dto.thursday
        friday = dto.friday
    }
    fun setDefaultMenu(id: Long) {
        defaultMenu = id
    }
}