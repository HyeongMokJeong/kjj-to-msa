package com.kjj.api.controller.store.user;

import com.kjj.api.aop.annotation.RestControllerClass;
import com.kjj.api.dto.calculate.CalculateMenuForGraphDto;
import com.kjj.api.service.store.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@RestControllerClass("/api/user/state")
public class StoreStateUserApiController {

    private final StoreService storeService;

    /**
     * 최근 5개의 정산 데이터로 상위 3개 메뉴별 판매량 집계
     *
     * @return List<CalculateMenuForGraphDto>
     */
    @Operation(summary = "최근 5영업일 메뉴별 판매량 조회", description = "메뉴 3종류만")
    @GetMapping("/menu")
    public ResponseEntity<List<CalculateMenuForGraphDto>> getPopularMenus() {
        return ResponseEntity.ok(storeService.getPopularMenus());
    }
}
