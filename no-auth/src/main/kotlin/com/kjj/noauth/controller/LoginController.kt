package com.kjj.noauth.controller

import com.kjj.noauth.dto.user.JoinOrLoginDto
import com.kjj.noauth.dto.user.UserInfoDto
import com.kjj.noauth.service.AuthService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/no-auth")
class LoginController(
    private val authService: AuthService
) {
    @PostMapping("/id")
    fun idLogin(response: HttpServletResponse, @RequestBody dto: JoinOrLoginDto): ResponseEntity<UserInfoDto> {
        return if (!dto.checkForm()) ResponseEntity.badRequest().body(
            UserInfoDto.loginFail("""
                데이터가 충분하지 않습니다.
                필드가 비어있거나 null 값이 담겨있습니다.
                dto : $dto
                """
            )
        ) else ResponseEntity.ok(authService.defaultLogin(response, dto))
    }

    @PostMapping("/keycloak")
    fun keycloakLogin(response: HttpServletResponse, @RequestParam("token") keycloakToken: String): ResponseEntity<UserInfoDto> {
        return ResponseEntity.ok(authService.keycloakLogin(response, keycloakToken))
    }
}