package com.kjj.calculate.repository;

import com.kjj.calculate.dto.CalculateMenuForGraphDto;
import com.kjj.calculate.entity.CalculateMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalculateMenuRepository extends JpaRepository<CalculateMenu, Long> {
    @Query("SELECT SUM(c.count) FROM CalculateMenu c")
    Integer getAllUsers();

    @Query("SELECT cm FROM CalculateMenu cm WHERE cm.calculate.id IN (:idList)")
    List<CalculateMenu> getPopularMenus(@Param("idList") List<Long> idList);
}
