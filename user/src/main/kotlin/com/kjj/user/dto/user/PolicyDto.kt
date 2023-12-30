package com.kjj.user.dto.user

import com.kjj.user.entity.UserPolicy

data class PolicyDto(
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val defaultMenu: Long?
) {
    companion object {
        fun from(policy: UserPolicy): PolicyDto {
            return PolicyDto(
                policy.monday,
                policy.tuesday,
                policy.wednesday,
                policy.thursday,
                policy.friday,
                policy.defaultMenu
            )
        }
    }
}