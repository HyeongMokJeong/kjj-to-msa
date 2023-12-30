package com.kjj.user.repository.order

import com.kjj.user.entity.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface OrderRepository: JpaRepository<Order, Long> {
    fun findByUserId(id: Long): List<Order>
    fun findByUserIdAndOrderDate(id: Long, date: LocalDate): Order?
    fun findFirstByUserIdOrderByIdDesc(id: Long): Order?
    fun findByUserIdOrderByIdDesc(userId: Long, pageable: Pageable): Page<Order>

    @Query("SELECT o FROM Order o WHERE o.user.id = :id AND YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month")
    fun findByUserIdAndOrderDate_YearAndOrderDate_Month(id: Long, year: Int, month: Int): List<Order>

    fun deleteAllByMenu(menuName: String)

    @Query("select o from Order o join fetch o.user u join fetch u.userMypage where o.id = :id")
    fun findByIdJoinUserAndMyPage(id: Long): Order?
}