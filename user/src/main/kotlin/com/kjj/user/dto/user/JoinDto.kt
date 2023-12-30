package com.kjj.user.dto.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

data class JoinDto(
    val username: String,
    var password: String
) {
    fun setEncodePassword(encoder: BCryptPasswordEncoder) {
        password = encoder.encode(password)
    }
}