package com.kjj.user.dto.user

import com.kjj.user.entity.User

data class UserDto(
    val id: Long?,
    val username: String,
    val password: String,
    val roles: String
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                user.id,
                user.username,
                user.password,
                user.roles
            )
        }
    }
}