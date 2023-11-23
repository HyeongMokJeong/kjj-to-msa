package com.kjj.api.repository.order;

import com.kjj.api.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(long l);

    Optional<Order> findByUserIdAndOrderDate(Long id, LocalDate date);

    Optional<Order> findFirstByUserIdOrderByIdDesc(Long id);

    Page<Order> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.user.id = :id AND YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month")
    List<Order> findByUserIdAndOrderDate_YearAndOrderDate_Month(Long id, int year, int month);

    void deleteAllByMenu(String menuName);

    @Query("select o from Order o join fetch o.user u join fetch u.userMypage where o.id = :id")
    Optional<Order> findByIdWithFetch(Long id);
}