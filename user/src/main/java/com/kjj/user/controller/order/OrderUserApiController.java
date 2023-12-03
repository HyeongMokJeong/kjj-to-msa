package com.kjj.user.controller.order;

import com.kjj.user.dto.order.LastOrderDto;
import com.kjj.user.dto.order.OrderDto;
import com.kjj.user.exception.CantFindByIdException;
import com.kjj.user.exception.WrongParameterException;
import com.kjj.user.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user/order")
public class OrderUserApiController {

    private final OrderService orderService;

    @PostMapping("/cancel/{year}/{month}/{day}")
    public ResponseEntity<OrderDto> cancelOrder(
            @RequestParam("userId") Long userId,
            @PathVariable int year, 
            @PathVariable int month, 
            @PathVariable int day) throws CantFindByIdException {
        return ResponseEntity.ok(orderService.cancelOrder(userId, year, month, day));
    }

    @PostMapping("/add/{menuId}/{year}/{month}/{day}")
    public ResponseEntity<OrderDto> addOrder(
            @RequestParam("userId") Long userId,
            @PathVariable Long menuId,
            @PathVariable int year,
            @PathVariable int month,
            @PathVariable int day) throws CantFindByIdException {
        return ResponseEntity.ok(orderService.addOrder(userId, menuId, year, month, day));
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<OrderDto>> getOrderMonth(
            @RequestParam("userId") Long userId,
            @PathVariable int year,
            @PathVariable int month) throws WrongParameterException {
        if (month <= 0 || month > 12) throw new WrongParameterException("month(1 ~ 12) : " + month);
        return ResponseEntity.ok(orderService.getOrderMonth(userId, year, month));
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<OrderDto> getOrderDay(
            @RequestParam("userId") Long userId,
            @PathVariable int year,
            @PathVariable int month,
            @PathVariable int day) throws WrongParameterException {
        if (month <= 0 || month > 12) throw new WrongParameterException("month(1 ~ 12) : " + month);
        return ResponseEntity.ok(orderService.getOrderDay(userId, year, month, day));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countUserOrders(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(orderService.countPages(userId));
    }

    @GetMapping("/show/{page}")
    public ResponseEntity<List<OrderDto>> getOrders(@RequestParam("userId") Long userId, @PathVariable int page) {
        return ResponseEntity.ok(orderService.getOrdersPage(userId, page));
    }

    @GetMapping("/last")
    public ResponseEntity<LastOrderDto> getLastOrder(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(orderService.getLastOrder(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderInfo(@PathVariable Long orderId) throws CantFindByIdException, WrongParameterException {
        return ResponseEntity.ok(orderService.getOrderInfo( orderId));
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<OrderDto> setPaymentTrue(@PathVariable Long orderId) throws CantFindByIdException, WrongParameterException {
        return ResponseEntity.ok(orderService.setPaymentTrue(orderId));
    }
    
    @GetMapping("/{orderId}/qr")
    public ResponseEntity<String> checkOrder(@PathVariable Long orderId) throws CantFindByIdException {
        orderService.checkOrder(orderId);
        return ResponseEntity.ok("order checked");
    }
}