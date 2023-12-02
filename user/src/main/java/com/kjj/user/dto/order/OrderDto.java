package com.kjj.user.dto.order;

import com.kjj.user.entity.Order;
import com.kjj.user.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private String menu;
    private int cost;
    private String orderDate;
    private boolean recognize;
    private boolean expired;
    private boolean payment;

    public static OrderDto from(Order order) {
        return new OrderDto(
                order.getId(),
                order.getMenu(),
                order.getCost(),
                DateUtil.makeLocaldateToFormatterString(order.getOrderDate()),
                order.isRecognize(),
                order.isExpired(),
                order.isPayment()
        );
    }
}
