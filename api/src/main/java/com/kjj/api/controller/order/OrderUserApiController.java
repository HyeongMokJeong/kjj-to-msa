package com.kjj.api.controller.order;

import com.kjj.api.aop.annotation.RestControllerClass;
import com.kjj.api.auth.JwtTemplate;
import com.kjj.api.auth.JwtUtil;
import com.kjj.api.dto.order.LastOrderDto;
import com.kjj.api.dto.order.OrderDto;
import com.kjj.api.exception.exceptions.CantFindByIdException;
import com.kjj.api.exception.exceptions.WrongParameter;
import com.kjj.api.exception.exceptions.WrongRequestDetails;
import com.kjj.api.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestControllerClass("/api/user/order")
public class OrderUserApiController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;
    private final JwtTemplate jwtTemplate = new JwtTemplate();

    /**
     * 사용자가 특정 연, 월, 일 이용안함 설정
     *
     * @param year - 연
     * @param month - 월
     * @param day - 일
     * @return OrderDto
     * @throws CantFindByIdException - 유저가 설정한 기본메뉴가 null이거나 메뉴가 없을 때 발생
     */
    @Operation(summary="수동으로 이용안함 설정")
    @PostMapping("/cancel/{year}/{month}/{day}")
    public ResponseEntity<OrderDto> cancelOrder(HttpServletRequest request, @PathVariable int year, @PathVariable int month, @PathVariable int day) throws CantFindByIdException, WrongRequestDetails {
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));
        return ResponseEntity.ok(orderService.cancelOrder(id, year, month, day));
    }

    /**
     * 사용자가 특정 연, 월, 일 이용함 설정
     *
     * @param menuId - 메뉴 id
     * @param year - 연
     * @param month - 월
     * @param day - 일
     * @return OrderDto
     * @throws CantFindByIdException - 유저가 설정한 기본메뉴가 null이거나 메뉴가 없을 때 발생
     */
    @Operation(summary="수동으로 이용함 설정")
    @PostMapping("/add/{menuId}/{year}/{month}/{day}")
    public ResponseEntity<OrderDto> addOrder(HttpServletRequest request, @PathVariable Long menuId, @PathVariable int year, @PathVariable int month, @PathVariable int day) throws CantFindByIdException, WrongRequestDetails {
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));
        return ResponseEntity.ok(orderService.addOrder(id, menuId, year, month, day));
    }

    /**
     * 연, 월 입력받아 User 이용정보 조회
     *
     * @param year - 연
     * @param month - 월
     * @return List<OrderDto>
     * @throws WrongRequestDetails - month가 1 ~ 12 사이가 아닐 때 발생
     */
    @Operation(summary="월 단위 이용정보 조회")
    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<OrderDto>> getOrderMonth(HttpServletRequest request, @PathVariable int year, @PathVariable int month) throws WrongRequestDetails {
        if (month <= 0 || month > 12) throw new WrongRequestDetails("month(1 ~ 12) : " + month);
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));
        return ResponseEntity.ok(orderService.getOrderMonth(id, year, month));
    }

    /**
     * 연, 월, 일 입력받아 User 이용정보 조회
     *
     * @param year - 연
     * @param month - 월
     * @param day - 일
     * @return OrderDto
     * @throws WrongRequestDetails - month가 1 ~ 12 사이가 아닐 때 발생
     */
    @Operation(summary="일 단위 이용정보 조회")
    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<OrderDto> getOrderDay(HttpServletRequest request, @PathVariable int year, @PathVariable int month, @PathVariable int day) throws WrongRequestDetails {
        if (month <= 0 || month > 12) throw new WrongRequestDetails("month(1 ~ 12) : " + month);
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));
        return ResponseEntity.ok(orderService.getOrderDay(id, year, month, day));
    }

    /**
     * User 전체 이용내역 페이지 개수 조회
     * 페이지 사이즈 = 5
     *
     * @return Integer - 페이지 사이즈보다 작으면 null
     */
    @Operation(summary="특정 유저의 전체 이용내역 개수 조회(페이지 구성용)", description="페이지 사이즈보다 작을 경우 null")
    @GetMapping("/count")
    public ResponseEntity<Integer> countUserOrders(HttpServletRequest request) {
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));
        return ResponseEntity.ok(orderService.countPages(id));
    }

    /**
     * 특정 페이지 이용내역 조회
     *
     * @param page - 몇 페이지인지
     * @return List<OrderDto>
     */
    @Operation(summary="유저의 n번 페이지 이용내역 조회", description="페이지 사이즈 = 10")
    @GetMapping("/show/{page}")
    public ResponseEntity<List<OrderDto>> getOrders(HttpServletRequest request, @PathVariable int page) {
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));
        return ResponseEntity.ok(orderService.getOrdersPage(id, page));
    }

    /**
     * 가장 최근 예약내역 조회
     *
     * @return LastOrderDto
     */
    @Operation(summary="유저의 가장 최근 이용내역 조회")
    @GetMapping("/last")
    public ResponseEntity<LastOrderDto> getLastOrder(HttpServletRequest request) {
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));
        return ResponseEntity.ok(orderService.getLastOrder(id));
    }

    @Operation(summary="예약 내역 정보 조회")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderInfo(HttpServletRequest request, @PathVariable Long orderId) throws CantFindByIdException, WrongParameter {
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));
        return ResponseEntity.ok(orderService.getOrderInfo(id, orderId));
    }

    @Operation(summary="예약 내역 payment True로 설정")
    @PostMapping("/{orderId}/payment")
    public ResponseEntity<OrderDto> setPaymentTrue(@PathVariable Long orderId) throws CantFindByIdException, WrongParameter {
        return ResponseEntity.ok(orderService.setPaymentTrue(orderId));
    }

    /**
     * QR에 담길 데이터
     *
     * @return LastOrderDto
     * @throws CantFindByIdException - Order 정보가 없을 때 발생
     */
    @Operation(summary="QR 내부 데이터")
    @GetMapping("/{orderId}/qr")
    public ResponseEntity<String> checkOrder(@PathVariable Long orderId) throws CantFindByIdException {
        orderService.checkOrder(orderId);
        return ResponseEntity.ok("order checked");
    }
}