package com.kjj.noauth.dto.user

import java.util.*

data class UserDto(
    val id: Long?,
    val username: String,
    val password: String,
    val roles: String
) {
    companion object {
        fun ofKeycloak(sub: String, roles: String): UserDto {
            val sb = StringBuilder()
            val random = Random()

            val length = 10
            for (i in 0 until length) sb.append(random.nextInt(10))

            return UserDto(
                null,
                sub,
                sb.toString(),
                roles
            )
        }
    }
}