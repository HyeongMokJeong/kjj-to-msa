package com.kjj.noauth.controller

import com.kjj.noauth.dto.HttpResponse
import com.kjj.noauth.dto.user.WithdrawDto
import com.kjj.noauth.service.KeycloakService
import com.kjj.noauth.service.UserService
import com.kjj.noauth.tool.jwt.JwtTemplate
import com.kjj.noauth.tool.jwt.JwtTool
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/no-auth/withdraw")
class WithdrawController(
    private val jwtTemplate: JwtTemplate,
    private val jwtTool: JwtTool,
    private val userService: UserService,
    private val keycloakService: KeycloakService
) {
    @DeleteMapping
    fun withdraw(request: HttpServletRequest, @RequestBody dto: WithdrawDto): ResponseEntity<HttpResponse<Boolean>> {
        val token = request.getHeader(jwtTemplate.headerString)
        val username: String = jwtTool.getUsernameFromToken(token)

        val result = userService.withdraw(username, dto)
        return if (result) {
            ResponseEntity.ok(HttpResponse(true, "회원탈퇴에 성공했습니다."))
        } else {
            ResponseEntity.badRequest().body(HttpResponse(false, "회원탈퇴에 실패했습니다."))
        }
    }

    @DeleteMapping("/keycloak")
    fun withdrawKeycloak(request: HttpServletRequest): ResponseEntity<HttpResponse<Boolean>> {
        val token = request.getHeader(jwtTemplate.headerString)
        val username: String = jwtTool.getUsernameFromToken(token)

        val result = keycloakService.withdrawSso(username)
        return if (result) {
            ResponseEntity.ok(HttpResponse(true, "회원탈퇴에 성공했습니다."))
        } else {
            ResponseEntity.badRequest().body(HttpResponse(false, "회원탈퇴에 실패했습니다."))
        }
    }
}