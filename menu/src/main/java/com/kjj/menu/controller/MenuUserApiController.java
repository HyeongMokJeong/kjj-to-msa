package com.kjj.menu.controller;

import com.kjj.menu.dto.MenuUserInfoDto;
import com.kjj.menu.service.MenuCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/menu/user")
@RestController
public class MenuUserApiController {

    private final MenuCacheService menuCacheService;

    @GetMapping
    public ResponseEntity<List<MenuUserInfoDto>> getMenus() {
        return ResponseEntity.ok(menuCacheService.getMenus().getDtos());
    }
}