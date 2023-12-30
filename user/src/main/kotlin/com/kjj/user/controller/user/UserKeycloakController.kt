package com.kjj.user.controller.user

import com.kjj.user.dto.user.JoinDto
import com.kjj.user.dto.user.UserDto
import com.kjj.user.service.sso.UserKeycloakService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/user")
class UserKeycloakController(
    val userKeycloakService: UserKeycloakService
) {
    @GetMapping("/exist")
    fun existsByUsername(@RequestParam("username") username: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userKeycloakService.existsByUsername(username))
    }

    @PostMapping("/join")
    fun join(@RequestBody dto: JoinDto): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userKeycloakService.join(dto))
    }

    @PostMapping("/join/keycloak")
    fun joinKeycloak(@RequestBody dto: UserDto): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userKeycloakService.joinKeycloak(dto))
    }
}