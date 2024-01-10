package com.kjj.noauth.tool.jwt

import org.springframework.stereotype.Component

@Component
class JwtTemplate(
    val secret: String = "capston2023kjj",
    val expiration: Long = 30,
    val tokenPrefix: String = "Bearer ",
    val headerString: String = "Authorization",
    val refreshHeaderString: String = "Refresh_Token",
    val refreshType: String = "Refresh"
    ) {
}