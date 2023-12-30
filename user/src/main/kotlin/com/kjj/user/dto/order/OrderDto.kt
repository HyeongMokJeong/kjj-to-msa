package com.kjj.user.dto.order

import com.kjj.user.entity.Order
import com.kjj.user.util.DateUtil

data class OrderDto(
    val id: Long?,
    val menu: String,
    val cost: Int,
    val orderDate: String,
    val recognize: Boolean,
    val expired: Boolean,
    val payment: Boolean
) {
    companion object {
        fun from(order: Order): OrderDto {
            return OrderDto(
                order.id,
                order.menu,
                order.cost,
                DateUtil.makeLocalDateToFormatterString(order.orderDate),
                order.recognize,
                order.expired,
                order.payment
            )
        }
    }
}