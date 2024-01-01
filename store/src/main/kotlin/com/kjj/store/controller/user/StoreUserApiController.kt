package com.kjj.store.controller.user

import com.kjj.store.dto.StoreDto
import com.kjj.store.dto.StoreStateDto
import com.kjj.store.exception.WrongParameterException
import com.kjj.store.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/store/user")
class StoreUserApiController(
    val storeService: StoreService
) {
    @GetMapping
    fun getStoreData(): ResponseEntity<StoreDto> {
        return ResponseEntity.ok(storeService.getStoreData())
    }

    @GetMapping("/off/{year}/{month}")
    fun getClosedDays(@PathVariable year: Int, @PathVariable month: Int): ResponseEntity<List<StoreStateDto>> {
        if (0 >= month || month > 12) throw WrongParameterException("month(1 ~ 12) : $month")
        return ResponseEntity.ok(storeService.getClosedDays(year, month))
    }
}