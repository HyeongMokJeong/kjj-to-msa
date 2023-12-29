package com.kjj.gateway.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
class JwtUtil (
    val jwtTemplate: JwtTemplate
){
    fun getUsernameFromToken(token: String): String {
        val removePrefixToken = token.replace(jwtTemplate.tokenPrefix, "")

        return JWT.require(Algorithm.HMAC256(jwtTemplate.secret)).build().verify(removePrefixToken)
            .getClaim(jwtTemplate.claimUsername).asString()
    }

    fun isTokenExpired(token: String): Boolean {
        val removePrefixToken = token.replace(jwtTemplate.tokenPrefix, "")

        try {
            JWT.require(Algorithm.HMAC256(jwtTemplate.secret)).build().verify(removePrefixToken).getClaim(jwtTemplate.claimExp).asDate()
        } catch (e: TokenExpiredException) {
            return true;
        }
        return false;
    }

    fun getIdFromToken(token: String): String {
        val removePrefixToken = token.replace(jwtTemplate.tokenPrefix, "")

        val originId = JWT.require(Algorithm.HMAC256(jwtTemplate.secret)).build().verify(removePrefixToken)
            .getClaim(jwtTemplate.claimId).asLong()
        return originId.toString()
    }

    fun getRolesFromToken(token: String): String {
        val removePrefixToken = token.replace(jwtTemplate.tokenPrefix, "")
        return JWT.require(Algorithm.HMAC256(jwtTemplate.secret)).build().verify(removePrefixToken)
            .getClaim(jwtTemplate.claimRoles).asString()
    }
}