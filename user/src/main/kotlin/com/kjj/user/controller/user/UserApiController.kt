package com.kjj.user.controller.user

import com.kjj.user.dto.info.UserInfoDto
import com.kjj.user.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user")
class UserApiController(
    val userService: UserService
) {
    @GetMapping("/info")
    fun getInfo(@RequestParam("userId") id: Long): ResponseEntity<UserInfoDto> {
        return ResponseEntity.ok(userService.getInfo(id))
    }
}