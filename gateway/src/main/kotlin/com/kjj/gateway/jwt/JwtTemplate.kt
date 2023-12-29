package com.kjj.gateway.jwt

import org.springframework.stereotype.Component

@Component
data class JwtTemplate (
    val secret: String = "capston2023kjj",
    val tokenPrefix: String = "Bearer ",
    val claimUsername: String = "username",
    val claimExp: String = "exp",
    val claimRoles: String = "roles",
    val claimId: String = "id"
)