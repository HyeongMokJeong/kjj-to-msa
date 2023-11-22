package com.kjj.api.service.order;

import com.google.zxing.WriterException;
import com.kjj.api.dto.order.LastOrderDto;
import com.kjj.api.dto.order.OrderDto;
import com.kjj.api.entity.order.Order;
import com.kjj.api.exception.exceptions.CantFindByIdException;
import com.kjj.api.exception.exceptions.WrongParameter;
import com.kjj.api.exception.exceptions.WrongRequestDetails;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order createNewOrder(Long userId, Long menuId, LocalDate date, boolean type) throws CantFindByIdException, WrongRequestDetails;

    OrderDto cancelOrder(Long id, int year, int month, int day) throws CantFindByIdException, WrongRequestDetails;

    OrderDto addOrder(Long id, Long menuId, int year, int month, int day) throws CantFindByIdException, WrongRequestDetails;

    int countPages(Long id);

    List<OrderDto> getOrders(Long id);

    List<OrderDto> getOrdersPage(Long id, int page);

    LastOrderDto getLastOrder(Long id);

    BufferedImage getOrderQr(Long id) throws WriterException;

    BufferedImage createQRCode(String data) throws WriterException;

    List<OrderDto> getOrderMonth(Long id, int year, int month);

    OrderDto getOrderDay(Long id, int year, int month, int day);

    void checkOrder(Long id) throws CantFindByIdException;

    OrderDto getOrderInfo(Long id, Long orderId) throws CantFindByIdException, WrongParameter;

    OrderDto setPaymentTrue(Long orderId) throws CantFindByIdException, WrongParameter;
}
