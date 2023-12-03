package com.kjj.menu.controller;

import com.kjj.menu.dto.MenuNameAndCostDto;
import com.kjj.menu.service.MenuCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/menu")
@RequiredArgsConstructor
public class ForClientController {

    private final MenuCacheService menuService;

    // user 서버에서 사용하는 menuId로 존재 여부 조회하는 api
    @GetMapping("/exist")
    public ResponseEntity<Boolean> existsById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(menuService.existsById(id));
    }

    // user 서버에서 사용하는 menuId로 메뉴 이름 조회하는 api
    @GetMapping("/name")
    public ResponseEntity<String> getName(@RequestParam("id") Long id) {
        return ResponseEntity.ok(menuService.getName(id));
    }

    // user 서버에서 사용하는 menuId로 메뉴 이름, 가격 조회하는 api
    @GetMapping("/name-cost")
    public ResponseEntity<MenuNameAndCostDto> getNameAndCost(@RequestParam("id") Long id) {
        return ResponseEntity.ok(menuService.getNameAndCost(id));
    }
}
