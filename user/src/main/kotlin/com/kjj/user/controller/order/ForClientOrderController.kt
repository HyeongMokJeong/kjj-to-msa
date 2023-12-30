package com.kjj.user.controller.order

import com.kjj.user.service.order.OrderService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user")
class ForClientOrderController(
    val orderService: OrderService
) {
    // menu 서버에서 메뉴 삭제 시 모든 예약내역을 삭제하는 api
    @DeleteMapping("/order")
    fun deleteAllByMenu(@RequestParam("name") menuName: String) {
        orderService.deleteAllByMenu(menuName)
    }
}