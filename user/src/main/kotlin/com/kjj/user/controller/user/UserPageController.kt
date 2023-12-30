package com.kjj.user.controller.user

import com.kjj.user.dto.user.MyPageDto
import com.kjj.user.dto.user.UsePointDto
import com.kjj.user.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/user/page")
class UserPageController(
    val userService: UserService
) {
    @GetMapping
    fun getMyPage(@RequestParam("userId") id: Long): ResponseEntity<MyPageDto> {
        return ResponseEntity.ok(userService.getMyPage(id))
    }

    @PostMapping("/point")
    fun usePoint(@RequestParam("userId") id: Long, @RequestBody dto: UsePointDto): ResponseEntity<Int> {
        return ResponseEntity.ok(userService.usePoint(id, dto))
    }
}