package com.kjj.noauth.controller

import com.kjj.noauth.service.UserService
import com.kjj.noauth.tool.jwt.JwtTool
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/no-auth")
class TestController(
    private val jwtTool: JwtTool,
    private val userService: UserService
) {
    @GetMapping("test")
    fun testToken(@RequestParam("username") username: String?): ResponseEntity<Map<String, String>> {
        val finalUsername = username ?: "manager"
        return ResponseEntity.ok(
            java.util.Map.of(
                "accessToken",
                jwtTool.createToken(userService.loadUserByUsername(finalUsername))
            )
        )
    }
}