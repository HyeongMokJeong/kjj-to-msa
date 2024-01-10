package com.kjj.noauth.tool.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.kjj.noauth.dto.user.UserDto
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtTool(
    private val jwtTemplate: JwtTemplate,
) {
    private val username: String = "username"

    fun createToken(userDto: UserDto): String {
        val localDateTime = LocalDateTime.now().plusMinutes(jwtTemplate.expiration)
        return JWT.create()
            .withSubject(jwtTemplate.tokenPrefix)
            .withExpiresAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
            .withClaim("id", userDto.id)
            .withClaim(username, userDto.username)
            .withClaim("roles", userDto.roles)
            .sign(Algorithm.HMAC256(jwtTemplate.secret))
    }

    fun createRefreshToken(userDto: UserDto): String {
        val localDateTime = LocalDateTime.now().plusDays(jwtTemplate.expiration)
        return JWT.create()
            .withSubject(jwtTemplate.tokenPrefix)
            .withExpiresAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
            .withClaim(username, userDto.username)
            .withClaim("type", jwtTemplate.refreshType)
            .sign(Algorithm.HMAC256(jwtTemplate.secret))
    }

    fun getUsernameFromToken(token: String): String {
        val payload = token.replace(jwtTemplate.tokenPrefix, "")
        return JWT.require(Algorithm.HMAC256(jwtTemplate.secret)).build().verify(payload)
            .getClaim(username).asString()
    }

    fun getTypeFromRefreshToken(token: String): String {
        val payload = token.replace(jwtTemplate.tokenPrefix, "")
        return JWT.require(Algorithm.HMAC256(jwtTemplate.secret)).build().verify(payload).getClaim("type").asString()
    }

    fun isTokenExpired(token: String?): Boolean {
        return try {
            val exp =
                JWT.require(Algorithm.HMAC256(jwtTemplate.secret)).build().verify(token).getClaim("exp").asDate()
            exp.before(Date())
        } catch (e: TokenExpiredException) {
            true
        }
    }
}