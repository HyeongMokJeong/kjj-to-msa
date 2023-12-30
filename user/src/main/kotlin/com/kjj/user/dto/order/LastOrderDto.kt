package com.kjj.user.dto.order

import com.kjj.user.entity.Order
import com.kjj.user.util.DateUtil

data class LastOrderDto(
    val id: Long?,
    val menu: String,
    val cost: Int,
    val orderDate: String,
    val recognize: Boolean
) {
    companion object {
        fun from(order: Order): LastOrderDto {
            return LastOrderDto(
                order.id,
                order.menu,
                order.cost,
                DateUtil.makeLocalDateToFormatterString(order.orderDate),
                order.recognize
            )
        }
    }
}