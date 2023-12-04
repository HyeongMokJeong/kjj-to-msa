package com.kjj.store.controller.manager;

import com.kjj.store.dto.*;
import com.kjj.store.exception.CantFindByIdException;
import com.kjj.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/store/manager")
public class StoreManagerApiController {

    private final StoreService storeService;

    @GetMapping("/setting")
    public ResponseEntity<StoreDto> isSetting() {
        return ResponseEntity.ok(storeService.isSetting());
    }

    @PostMapping("/setting")
    public ResponseEntity<StoreDto> setSetting(@RequestBody StoreSettingDto dto) {
        return ResponseEntity.ok(storeService.setSetting(dto));
    }

    @PostMapping("/image")
    public ResponseEntity<String> setStoreImage(@RequestPart(value = "file") MultipartFile file) throws IOException, CantFindByIdException {
        storeService.setStoreImage(file);
        return ResponseEntity.ok("반영되었습니다.");
    }

    @PatchMapping("/title")
    public ResponseEntity<StoreDto> updateStoreTitle(@RequestBody StoreTitleDto dto) throws CantFindByIdException{
        return ResponseEntity.ok(storeService.updateStoreTitle(dto));
    }

    @PatchMapping("/info")
    public ResponseEntity<StoreDto> updateStoreInfo(@RequestBody StoreInfoDto dto) throws CantFindByIdException {
        return ResponseEntity.ok(storeService.updateStoreInfo(dto));
    }

    @PostMapping("/off/{year}/{month}/{day}")
    public ResponseEntity<StoreStateDto> setOff(@RequestBody StoreOffDto off, @PathVariable int year, @PathVariable int month, @PathVariable int day) {
        return ResponseEntity.ok(storeService.setOff(off, year, month, day));
    }
}
