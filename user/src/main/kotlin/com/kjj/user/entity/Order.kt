package com.kjj.user.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "orders",
    indexes = arrayOf(
        Index(name = "order_date_index", columnList = "orderDate"))
)
class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    var menu: String,
    val cost: Int,
    val orderDate: LocalDate,
    var recognize: Boolean,
    var expired: Boolean,
    var payment: Boolean
){
    companion object {
        fun createNewOrder(
            user: User,
            menu: String,
            cost: Int,
            date: LocalDate,
            type: Boolean)
        : Order {
            return Order(
                null,
                user,
                menu,
                cost,
                date,
                type,
                false,
                false)
        }
    }

    fun setMenuAndRecognizeTrue(menuName: String) {
        menu = menuName
        recognize = true
    }

    fun setRecognizeToCancel() {
        recognize = false
    }

    fun setExpiredTrue() {
        expired = true
    }

    fun setPaymentTrue() {
        payment = true
    }
}