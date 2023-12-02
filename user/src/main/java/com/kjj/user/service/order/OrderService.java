package com.kjj.user.service.order;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kjj.user.dto.order.LastOrderDto;
import com.kjj.user.dto.order.OrderDto;
import com.kjj.user.entity.Order;
import com.kjj.user.entity.UserMyPage;
import com.kjj.user.entity.UserPolicy;
import com.kjj.user.exception.CantFindByIdException;
import com.kjj.user.repository.order.OrderRepository;
import com.kjj.user.repository.user.UserRepository;
import com.kjj.user.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService{

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final DateUtil dateUtil;

    private static final int PAGE_SIZE = 10;

    private Long getDefaultMenuId(Long userId) throws CantFindByIdException {
//        UserPolicy policy = userRepository.findById(userId).orElseThrow(() -> new CantFindByIdException("""
//                해당 id를 가진 user가 없습니다.
//                userId : """ + userId)).getUserPolicy();
//
//        if (policy == null) throw new CantFindByIdException("""
//                user가 UserPolicy를 가지고 있지 않습니다.
//                userId : """ + userId);
//
//        Long defaultMenu = policy.getDefaultMenu();
//        if (defaultMenu == null) return null;
//        else return menuRepository.findById(defaultMenu).orElseGet( () -> {
//                    policy.setDefaultMenu(null);
//                    return null;
//                }
//        ).getId();
        return null;
    }

    @Transactional
    public Order createNewOrder(Long userId, Long menuId, LocalDate date, boolean type) throws CantFindByIdException {
//        if (menuId == null) throw new WrongRequestDetails("""
//                전달된 menuId가 null 입니다.
//                menuId : """, menuId);
//
//        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new CantFindByIdException("""
//                menuId는 전달되었으나, 해당 id를 가진 메뉴가 존재하지 않습니다.
//                menuId : """, menuId));
//
//        Order order = Order.createNewOrder(userRepository.getReferenceById(userId), menu.getName(), menu.getCost(), date, type);
//
//        return orderRepository.save(order);
        return null;
    }

    @Transactional
    public OrderDto cancelOrder(Long userId, int year, int month, int day) throws CantFindByIdException {
        LocalDate date = dateUtil.makeLocalDate(year, month, day);
        Order order = orderRepository.findByUserIdAndOrderDate(userId, date).orElse(null);

        if (order == null) order = orderRepository.save(createNewOrder(userId, getDefaultMenuId(userId), date, false));
        else order.setRecognizeToCancel();
        return OrderDto.from(order);
    }

    @Transactional
    public OrderDto addOrder(String username, Long menuId, int year, int month, int day) throws CantFindByIdException {
//        LocalDate date = dateUtil.makeLocalDate(year, month, day);
//        Order order = orderRepository.findByUserIdAndOrderDate(id, date).orElse(null);
//
//        if (order == null) order = orderRepository.save(createNewOrder(id, menuId, date, true));
//        else {
//            order.setMenuAndRecognizeTrue(menuRepository.findById(menuId).orElseThrow(() -> new CantFindByIdException("""
//                    해당 id를 가진 menu를 찾을 수 없습니다.
//                    menuId : """, menuId)));
//        }
//        return OrderDto.from(order);
        return null;
    }

    
    @Transactional(readOnly = true)
    public int countPages(Long userId) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        return orderRepository.findByUserIdOrderByIdDesc(userId, pageable).getTotalPages();
    }


    
    @Transactional(readOnly = true)
    public List<OrderDto> getOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .map(OrderDto::from)
                .toList();
    }

    
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersPage(Long userId, int page) {
        Page<Order> orderPage = orderRepository.findByUserIdOrderByIdDesc(userId, PageRequest.of(page, PAGE_SIZE));

        return orderPage.getContent()
                .stream()
                .map(OrderDto::from)
                .toList();
    }

    
    @Transactional(readOnly = true)
    public LastOrderDto getLastOrder(Long userId){
        Optional<Order> order = orderRepository.findFirstByUserIdOrderByIdDesc(userId);

        return order.map(LastOrderDto::from)
                .orElse(null);
    }

    
    public BufferedImage getOrderQr(Long orderId) throws WriterException {
        String domain = "http://localhost:8080";
        String endPoint = "/api/user/order/" + orderId + "/qr";

        return createQRCode(domain + endPoint);
    }

    
    public BufferedImage createQRCode(String data) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 100, 100);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    
    @Transactional(readOnly = true)
    public List<OrderDto> getOrderMonth(Long userId, int year, int month) {
        List<Order> orders = orderRepository.findByUserIdAndOrderDate_YearAndOrderDate_Month(userId, year, month);

        return orders.stream()
                .map(OrderDto::from)
                .toList();
    }

    
    @Transactional(readOnly = true)
    public OrderDto getOrderDay(Long userId, int year, int month, int day) {
        Optional<Order> order = orderRepository.findByUserIdAndOrderDate(userId, dateUtil.makeLocalDate(year, month, day));

        return order.map(OrderDto::from)
                .orElse(null);
    }

    
    @Transactional
    public void checkOrder(Long orderId) throws CantFindByIdException {
        Order order = orderRepository.findByIdWithFetch(orderId).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 Order 데이터를 찾을 수 없습니다.
                orderId : """ + orderId));
        if (!order.isExpired()) {
            order.setExpiredTrue();

            int point = 50;
            UserMyPage myPage = order.getUser().getUserMypage();
            myPage.updatePoint(point);
        }
    }

    
    @Transactional(readOnly = true)
    public OrderDto getOrderInfo(Long orderId) throws CantFindByIdException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 Order 데이터를 찾을 수 없습니다.
                orderId : """ + orderId));

        return OrderDto.from(order);
    }

    
    @Transactional
    public OrderDto setPaymentTrue(Long orderId) throws CantFindByIdException  {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 Order 데이터를 찾을 수 없습니다.
                orderId : """ + orderId));
        order.setPaymentTrue();

        return OrderDto.from(order);
    }
}