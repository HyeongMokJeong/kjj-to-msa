package com.kjj.user.controller.manager

import com.kjj.user.dto.info.ManagerInfoDto
import com.kjj.user.service.manager.ManagerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/manager")
class ManagerApiController(
    val managerService: ManagerService
) {
    @GetMapping("/info")
    fun getInfo(@RequestParam("username") username: String): ResponseEntity<ManagerInfoDto> {
        return ResponseEntity.ok(managerService.getInfo(username))
    }
}