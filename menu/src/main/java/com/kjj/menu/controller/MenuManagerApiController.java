package com.kjj.menu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kjj.menu.client.ImageClient;
import com.kjj.menu.dto.*;
import com.kjj.menu.exception.CantFindByIdException;
import com.kjj.menu.exception.WrongParameterException;
import com.kjj.menu.service.MenuCacheService;
import com.kjj.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/menu/manager")
@RequiredArgsConstructor
public class MenuManagerApiController {

    private final MenuService menuService;
    private final MenuCacheService menuCacheService;
    private final ImageClient imageClient;

    private final String uploadDir = "img/menu";

    @GetMapping
    public ResponseEntity<List<MenuManagerInfoDto>> getMenusForManager() {
        return ResponseEntity.ok(menuService.getMenusForManager());
    }

    @GetMapping("/planner")
    public ResponseEntity<Boolean> isPlanned() {
        return ResponseEntity.ok(menuService.isPlanned());
    }

    @PostMapping("/{id}/planner")
    public ResponseEntity<MenuDto> setPlanner(@PathVariable Long id) throws CantFindByIdException {
        return ResponseEntity.ok(menuService.setPlanner(id));
    }

    @PatchMapping("/{id}/change/planner")
    public ResponseEntity<MenuDto> changePlanner(@PathVariable Long id) throws CantFindByIdException {
        return ResponseEntity.ok(menuService.changePlanner(id));
    }

    @PostMapping
    public ResponseEntity<MenuDto> addMenu(@RequestPart("data") MenuUpdateDto dto, @RequestPart(value = "file", required = false)MultipartFile file) throws WrongParameterException, IOException {
        if (dto == null) throw new WrongParameterException("dto : null");
        else if (!dto.check()) throw new WrongParameterException("dto : " + dto);

        String filePath = (file != null) ? imageClient.uploadImage(file, uploadDir) : null;
        return ResponseEntity.ok(menuCacheService.addMenu(dto, filePath));
    }

    @PatchMapping("/{menuId}/food/{foodId}")
    public ResponseEntity<String> setFood(@PathVariable Long menuId, @PathVariable Long foodId) throws CantFindByIdException {
        menuService.setFood(menuId, foodId);
        return ResponseEntity.ok("등록 완료");
    }

    @GetMapping("/food")
    public ResponseEntity<List<MenuFoodDto>> getFood() {
        return ResponseEntity.ok(menuCacheService.getFood().getDtos());
    }

    @GetMapping("/food/{id}")
    public ResponseEntity<MenuFoodDto> getOneFood(@PathVariable Long id) throws CantFindByIdException {
        return ResponseEntity.ok(menuService.getOneFood(id));
    }

    @PostMapping("/food")
    public ResponseEntity<MenuFoodDto> addFood(@RequestParam("name") String name, @RequestBody Map<String, Integer> data) throws JsonProcessingException {
        return ResponseEntity.ok(menuService.addFood(name, data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MenuInfoDto> updateMenu(@RequestPart("data") MenuUpdateDto dto, @RequestPart(value = "file", required = false)MultipartFile file, @PathVariable Long id) throws CantFindByIdException, IOException{
        return ResponseEntity.ok(menuCacheService.updateMenu(dto, file, id, uploadDir));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MenuDto> deleteMenu(@PathVariable Long id) throws CantFindByIdException {
        return ResponseEntity.ok(menuCacheService.deleteMenu(id));
    }

    @PatchMapping("/{id}/sold/{type}")
    public ResponseEntity<MenuDto> setSoldOut(@PathVariable Long id, @PathVariable String type) throws CantFindByIdException {
        return ResponseEntity.ok(menuCacheService.setSoldOut(id, type));
    }
}