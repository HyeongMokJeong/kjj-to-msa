package com.kjj.calculate.controller.user;

import com.kjj.calculate.dto.CalculateMenuForGraphDto;
import com.kjj.calculate.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/state/user")
public class CalculateUserApiController {

    private final StoreService storeService;

    @GetMapping("/menu")
    public ResponseEntity<List<CalculateMenuForGraphDto>> getPopularMenus() {
        return ResponseEntity.ok(storeService.getPopularMenus());
    }
}
