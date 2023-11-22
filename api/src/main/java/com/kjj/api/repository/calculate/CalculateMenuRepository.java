package com.kjj.api.repository.calculate;

import com.kjj.api.dto.calculate.CalculateMenuForGraphDto;
import com.kjj.api.entity.calculate.CalculateMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalculateMenuRepository extends JpaRepository<CalculateMenu, Long> {
    @Query("SELECT SUM(c.count) FROM CalculateMenu c")
    Integer getAllUsers();

    @Query("select new com.kjj.api.dto.calculate.CalculateMenuForGraphDto(cm.menu, sum(cm.count)) from CalculateMenu cm where cm.calculate.id in (:idList) group by cm.menu")
    List<CalculateMenuForGraphDto> getPopularMenus(@Param("idList") List<Long> idList);
}
