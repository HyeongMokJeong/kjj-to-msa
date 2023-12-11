package com.jhm.controller;

import com.jhm.client.OrderClient;
import com.jhm.client.UserClient;
import com.jhm.dto.user.JoinDto;
import com.jhm.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/client/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderClient orderClient;

    // 메뉴 삭제 시 모든 예약내역 삭제하는 api
    @DeleteMapping
    public ResponseEntity<String> deleteAllByMenu(@RequestParam("name") String menuName) {
        orderClient.deleteAllByMenu(menuName);
        return ResponseEntity.ok("Request Success");
    }
}
