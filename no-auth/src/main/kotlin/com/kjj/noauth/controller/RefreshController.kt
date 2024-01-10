package com.kjj.noauth.controller

import com.kjj.noauth.dto.jwt.TokenRefreshResponseDto
import com.kjj.noauth.exception.JwtRefreshException
import com.kjj.noauth.service.AuthService
import com.kjj.noauth.tool.jwt.JwtTemplate
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/no-auth/refresh")
class RefreshController(
    private val authService: AuthService,
    private val jwtTemplate: JwtTemplate
) {
    @PostMapping
    fun refreshJwt(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<TokenRefreshResponseDto> {
        var refreshToken = request.getHeader(jwtTemplate.headerString)
        refreshToken = refreshToken.replace(jwtTemplate.tokenPrefix, "")
        return try {
            ResponseEntity.ok(authService.refreshJwt(response, refreshToken))
        } catch (e: JwtRefreshException) {
            ResponseEntity.badRequest().body(TokenRefreshResponseDto.refreshFail(e))
        }
    }
}