package com.jhm.controller;

import com.jhm.client.MenuClient;
import com.jhm.dto.menu.MenuNameAndCostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/client/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuClient menuClient;

    // menuId로 메뉴가 존제하는지 확인하는 api
    @GetMapping("/exist")
    public ResponseEntity<Boolean> existsById(@RequestParam("menuId") Long menuId) {
        return ResponseEntity.ok(menuClient.existsById(menuId));
    }

    // menuId로 메뉴 이름과 가격을 조회하는 api
    @GetMapping("/name-cost")
    public ResponseEntity<MenuNameAndCostDto> findNameAndCostById(@RequestParam("menuId") Long menuId) {
        return ResponseEntity.ok(menuClient.findNameAndCostById(menuId));
    }

    // menuId로 메뉴 이름만 조회하는 api
    @GetMapping("/name")
    public ResponseEntity<String> findNameById(@RequestParam("menuId") Long menuId) {
        return ResponseEntity.ok(menuClient.findNameById(menuId));
    }
}
