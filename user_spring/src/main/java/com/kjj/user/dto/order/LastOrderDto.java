package com.kjj.user.dto.order;

import com.kjj.user.entity.Order;
import com.kjj.user.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LastOrderDto {
    private Long id;

    private String menu;
    private int cost;
    private String orderDate;
    private boolean recognize;

    public static LastOrderDto from(Order order) {
        return new LastOrderDto(
                order.getId(),
                order.getMenu(),
                order.getCost(),
                DateUtil.makeLocaldateToFormatterString(order.getOrderDate()),
                order.isRecognize()
        );
    }
}
