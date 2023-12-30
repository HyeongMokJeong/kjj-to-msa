package com.kjj.user.controller.user

import com.kjj.user.dto.user.UserDto
import com.kjj.user.service.sso.UserKeycloakService
import com.kjj.user.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/user")
class ForClientUserController(
    val userService: UserService,
    val userKeycloakService: UserKeycloakService
) {
    // no-auth 서버에서 탈퇴요청 수신 시 유저 삭제를 위해 사용하는 API
    @DeleteMapping("/withdraw")
    fun withdraw(@RequestParam("username") username: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.withdraw(username))
    }

    // no-auth 서버에서 user정보 조회하기 위해 사용하는 api
    @GetMapping("/info/all")
    fun findByUsername(@RequestParam("username") username: String): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userKeycloakService.findByUsername(username))
    }

    // no-auth 서버에서 탈퇴하기 위해 비밀번호 대조하는 api
    @GetMapping("/check/password")
    fun checkPasswordForWithdraw(
        @RequestParam("username") username: String,
        @RequestParam("password") password: String
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.checkPassword(username, password))
    }

    // menu 서버에서 메뉴가 삭제되었을 때 설정되어있던 정책을 null로 바꾸는 api
    @PatchMapping("/policy/menu-del")
    fun clearPolicyByDeletedMenu(@RequestParam("id") menuId: Long) {
        userService.clearPolicyByDeletedMenu(menuId)
    }
}