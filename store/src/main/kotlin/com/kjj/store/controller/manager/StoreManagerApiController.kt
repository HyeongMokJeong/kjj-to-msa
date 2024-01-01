package com.kjj.store.controller.manager

import com.kjj.store.dto.*
import com.kjj.store.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/store/manager")
class StoreManagerApiController(
    val storeService: StoreService
) {
    @GetMapping("/setting")
    fun isSetting(): ResponseEntity<StoreDto> {
        return ResponseEntity.ok(storeService.isSetting())
    }

    @PostMapping("/setting")
    fun setSetting(@RequestBody dto: StoreSettingDto): ResponseEntity<StoreDto> {
        return ResponseEntity.ok(storeService.setSetting(dto))
    }

    @PostMapping("/image")
    fun setStoreImage(@RequestPart(value = "file") file: MultipartFile): ResponseEntity<String> {
        storeService.setStoreImage(file)
        return ResponseEntity.ok("반영되었습니다.")
    }

    @PatchMapping("/title")
    fun updateStoreTitle(@RequestBody dto: StoreTitleDto): ResponseEntity<StoreDto> {
        return ResponseEntity.ok(storeService.updateStoreTitle(dto))
    }

    @PatchMapping("/info")
    fun updateStoreInfo(@RequestBody dto: StoreInfoDto): ResponseEntity<StoreDto> {
        return ResponseEntity.ok(storeService.updateStoreInfo(dto))
    }

    @PostMapping("/off/{year}/{month}/{day}")
    fun setOff(
        @RequestBody off: StoreOffDto,
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable day: Int
    ): ResponseEntity<StoreStateDto> {
        return ResponseEntity.ok(storeService.setOff(off, year, month, day))
    }
}