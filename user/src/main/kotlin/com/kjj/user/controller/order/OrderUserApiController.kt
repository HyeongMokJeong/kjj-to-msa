package com.kjj.user.controller.order

import com.kjj.user.dto.order.LastOrderDto
import com.kjj.user.dto.order.OrderDto
import com.kjj.user.exception.WrongParameterException
import com.kjj.user.service.order.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/user/order")
class OrderUserApiController(
    val orderService: OrderService
) {
    @PostMapping("/cancel/{year}/{month}/{day}")
    fun cancelOrder(
        @RequestParam("userId") userId: Long,
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable day: Int
    ): ResponseEntity<OrderDto> {
        return ResponseEntity.ok(orderService.cancelOrder(userId, year, month, day))
    }

    @PostMapping("/add/{menuId}/{year}/{month}/{day}")
    fun addOrder(
        @RequestParam("userId") userId: Long,
        @PathVariable menuId: Long,
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable day: Int
    ): ResponseEntity<OrderDto> {
        return ResponseEntity.ok(
            orderService.addOrder(
                userId,
                menuId, year, month, day
            )
        )
    }

    @GetMapping("/{year}/{month}")
    fun getOrderMonth(
        @RequestParam("userId") userId: Long,
        @PathVariable year: Int,
        @PathVariable month: Int
    ): ResponseEntity<List<OrderDto>> {
        if (month <= 0 || month > 12) throw WrongParameterException("month(1 ~ 12) : $month")
        return ResponseEntity.ok(orderService.getOrderMonth(userId, year, month))
    }

    @GetMapping("/{year}/{month}/{day}")
    fun getOrderDay(
        @RequestParam("userId") userId: Long,
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable day: Int
    ): ResponseEntity<OrderDto> {
        if (month <= 0 || month > 12) throw WrongParameterException("month(1 ~ 12) : $month")
        return ResponseEntity.ok(orderService.getOrderDay(userId, year, month, day))
    }

    @GetMapping("/count")
    fun countUserOrders(@RequestParam("userId") userId: Long): ResponseEntity<Int> {
        return ResponseEntity.ok(orderService.countPages(userId))
    }

    @GetMapping("/show/{page}")
    fun getOrders(@RequestParam("userId") userId: Long, @PathVariable page: Int): ResponseEntity<List<OrderDto>> {
        return ResponseEntity.ok(orderService.getOrdersPage(userId, page))
    }

    @GetMapping("/last")
    fun getLastOrder(@RequestParam("userId") userId: Long): ResponseEntity<LastOrderDto> {
        return ResponseEntity.ok(orderService.getLastOrder(userId))
    }

    @GetMapping("/{orderId}")
    fun getOrderInfo(@PathVariable orderId: Long): ResponseEntity<OrderDto> {
        return ResponseEntity.ok(orderService.getOrderInfo(orderId))
    }

    @PostMapping("/{orderId}/payment")
    fun setPaymentTrue(@PathVariable orderId: Long): ResponseEntity<OrderDto> {
        return ResponseEntity.ok(orderService.setPaymentTrue(orderId))
    }

    @GetMapping("/{orderId}/qr")
    fun checkOrder(@PathVariable orderId: Long): ResponseEntity<String> {
        orderService.checkOrder(orderId)
        return ResponseEntity.ok("order checked")
    }
}