package com.kjj.user.dto.info

import com.kjj.user.entity.User
import java.time.LocalDate

data class UserInfoDto(
    val id: Long?,
    val username: String,
    val loginDate: LocalDate?
) {
    companion object {
        fun from(user: User): UserInfoDto {
            return UserInfoDto(
                user.id,
                user.username,
                user.loginDate
            )
        }
    }
}