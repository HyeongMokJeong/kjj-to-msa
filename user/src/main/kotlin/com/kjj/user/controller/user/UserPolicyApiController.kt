package com.kjj.user.controller.user

import com.kjj.user.dto.user.DatePolicyDto
import com.kjj.user.dto.user.PolicyDto
import com.kjj.user.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/user/policy")
class UserPolicyApiController(
    val userService: UserService
) {
    // 이용자 요일 정책 설정하는 api
    @PatchMapping("/date")
    fun setUserDatePolicy(
        @RequestParam("userId") id: Long,
        @RequestBody dto: DatePolicyDto
    ): ResponseEntity<PolicyDto> {
        return ResponseEntity.ok(userService.setUserDatePolicy(dto, id))
    }

    // 이용자 메뉴 정책 설정하는 api
    @PatchMapping("/menu/{menuId}")
    fun setUserMenuPolicy(
        @RequestParam("userId") userId: Long,
        @PathVariable menuId: Long
    ): ResponseEntity<PolicyDto> {
        return ResponseEntity.ok(userService.setUserMenuPolicy(userId, menuId))
    }

    // 유저 정책 조회하는 api
    @GetMapping("/date")
    fun getUserPolicy(@RequestParam("userId") id: Long): ResponseEntity<PolicyDto> {
        return ResponseEntity.ok(userService.getUserPolicy(id))
    }
}