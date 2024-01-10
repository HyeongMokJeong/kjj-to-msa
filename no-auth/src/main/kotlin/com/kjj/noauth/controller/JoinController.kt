package com.kjj.noauth.controller

import com.kjj.noauth.dto.user.JoinOrLoginDto
import com.kjj.noauth.exception.WrongRequestBodyException
import com.kjj.noauth.service.KeycloakService
import com.kjj.noauth.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/no-auth/join")
class JoinController(
    private val userService: UserService,
    private val keycloakService: KeycloakService
) {
    @PostMapping
    fun join(@RequestBody dto: JoinOrLoginDto): ResponseEntity<Boolean> {
        if (!dto.checkForm()) throw WrongRequestBodyException("""
            데이터 형식이 올바르지 않습니다.
            dto : $dto
            """
        )
        return ResponseEntity.ok(userService.join(dto))
    }

    @PostMapping("/keycloak")
    fun joinToKeycloakServer(@RequestBody dto: JoinOrLoginDto): ResponseEntity<String> {
        if (!dto.checkForm()) throw WrongRequestBodyException("""
            데이터 형식이 올바르지 않습니다.
            dto : $dto
            """
        )
        keycloakService.joinKeycloakServer(dto)
        return ResponseEntity.ok("Keyclock 회원가입에 성공했습니다.")
    }

    @GetMapping("/check")
    fun checkLoginId(@RequestParam("username") username: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.checkLoginId(username))
    }
}