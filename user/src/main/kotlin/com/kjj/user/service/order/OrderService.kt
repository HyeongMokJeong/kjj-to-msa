package com.kjj.user.service.order

import com.kjj.user.client.MenuClient
import com.kjj.user.dto.order.LastOrderDto
import com.kjj.user.dto.order.OrderDto
import com.kjj.user.entity.Order
import com.kjj.user.exception.CantFindByIdException
import com.kjj.user.repository.order.OrderRepository
import com.kjj.user.repository.user.UserRepository
import com.kjj.user.util.DateUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class OrderService(
    val userRepository: UserRepository,
    val menuClient: MenuClient,
    val orderRepository: OrderRepository,
) {
    companion object{
        val pageSize: Int = 10
    }

    private fun getDefaultMenuId(userId: Long): Long {
        val policy = userRepository.findByIdJoinPolicy(userId)?.userPolicy
            ?: throw CantFindByIdException(
                """
                해당 id를 가진 user가 없습니다.
                userId : $userId
                """
            )

        if (policy.defaultMenu == null) {
            throw CantFindByIdException(
                """
                user가 UserPolicy를 가지고 있지 않습니다.
                userId : $userId
                """
            )
        }
        if (!menuClient.existsById(policy.defaultMenu!!)) policy.defaultMenu = null

        return policy.defaultMenu!!
    }

    private fun createNewOrder(userId: Long, menuId: Long, date: LocalDate, type: Boolean): Order {
        val dto = menuClient.findNameAndCostById(menuId)
        val order = Order.createNewOrder(userRepository.getReferenceById(userId), dto.name, dto.cost, date, type)

        return orderRepository.save(order)
    }

    @Transactional
    fun cancelOrder(userId: Long, year: Int, month: Int, day: Int): OrderDto {
        val date: LocalDate = DateUtil.makeLocalDate(year, month, day)
        var order: Order? = orderRepository.findByUserIdAndOrderDate(userId, date)

        if (order == null) {
            order = orderRepository.save(createNewOrder(userId, getDefaultMenuId(userId), date, false))
        } else {
            order.setRecognizeToCancel()
        }
        return OrderDto.from(order)
    }

    @Transactional
    fun addOrder(userId: Long, menuId: Long, year: Int, month: Int, day: Int): OrderDto {
        val date: LocalDate = DateUtil.makeLocalDate(year, month, day)
        var order: Order? = orderRepository.findByUserIdAndOrderDate(userId, date)

        if (order == null) {
            order = orderRepository.save(createNewOrder(userId, menuId, date, true))
        } else {
            order.setMenuAndRecognizeTrue(menuClient.findNameById(menuId))
        }
        return OrderDto.from(order)
    }

    @Transactional(readOnly = true)
    fun countPages(userId: Long): Int {
        val pageable: Pageable = PageRequest.of(0, pageSize)
        return orderRepository.findByUserIdOrderByIdDesc(userId, pageable).totalPages
    }

    @Transactional(readOnly = true)
    fun getOrders(userId: Long): List<OrderDto> {
        val orders = orderRepository.findByUserId(userId)
        return orders.stream()
            .map(OrderDto::from)
            .toList()
    }

    @Transactional(readOnly = true)
    fun getOrdersPage(userId: Long, page: Int): List<OrderDto> {
        val orderPage = orderRepository.findByUserIdOrderByIdDesc(
            userId, PageRequest.of(page, pageSize)
        )
        return orderPage.content
            .stream()
            .map(OrderDto::from)
            .toList()
    }

    @Transactional(readOnly = true)
    fun getLastOrder(userId: Long): LastOrderDto? {
        val order: Order? = orderRepository.findFirstByUserIdOrderByIdDesc(userId)
        return order?.let { LastOrderDto.from(it) }
    }

    @Transactional(readOnly = true)
    fun getOrderMonth(userId: Long, year: Int, month: Int): List<OrderDto> {
        val orders: List<Order> = orderRepository.findByUserIdAndOrderDate_YearAndOrderDate_Month(userId, year, month)
        return orders.stream()
            .map(OrderDto::from)
            .toList()
    }

    @Transactional(readOnly = true)
    fun getOrderDay(userId: Long, year: Int, month: Int, day: Int): OrderDto? {
        val order: Order? = orderRepository.findByUserIdAndOrderDate(userId, DateUtil.makeLocalDate(year, month, day))
        return order?.let { OrderDto.from(it) }
    }

    @Transactional
    fun checkOrder(orderId: Long) {
        val order: Order = orderRepository.findByIdJoinUserAndMyPage(orderId)
            ?: throw CantFindByIdException(
                """
                해당 id를 가진 Order 데이터를 찾을 수 없습니다.
                orderId : $orderId
                """
            )
        if (!order.expired) {
            order.setExpiredTrue()
            val point = 50
            order.user.userMypage.updatePoint(point)
        }
    }

    @Transactional(readOnly = true)
    fun getOrderInfo(orderId: Long): OrderDto {
        val order = orderRepository.findById(orderId).orElseThrow {
            CantFindByIdException(
                """
                해당 id를 가진 Order 데이터를 찾을 수 없습니다.
                orderId : $orderId
                """
            )
        }
        return OrderDto.from(order)
    }

    @Transactional
    fun setPaymentTrue(orderId: Long): OrderDto {
        val order = orderRepository.findById(orderId).orElseThrow {
            CantFindByIdException(
                """
                해당 id를 가진 Order 데이터를 찾을 수 없습니다.
                orderId : $orderId
                """
            )
        }
        order.setPaymentTrue()
        return OrderDto.from(order)
    }

    fun deleteAllByMenu(menuName: String) {
        orderRepository.deleteAllByMenu(menuName)
    }
}