package com.kjj.user.controller.order;

import com.kjj.user.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class ForClientOrderController {

    private final OrderService orderService;

    // menu 서버에서 메뉴 삭제 시 모든 예약내역을 삭제하는 api
    @DeleteMapping("/order")
    public void deleteAllByMenu(@RequestParam("name") String menuName) {
        orderService.deleteAllByMenu(menuName);
    }
}
