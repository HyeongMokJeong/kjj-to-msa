package com.kjj.menu.repository;

import com.kjj.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Boolean existsByName(String name);

    @Query("select m from Menu m left join fetch m.menuInfo left join fetch m.menuFood")
    List<Menu> findAllWithMenuInfoAndMenuFood();

    Optional<Menu> findByUsePlanner(boolean b);

    Boolean existsByUsePlannerTrue();

    @Query("select m from Menu m left join fetch m.menuInfo where m.id = :id")
    Optional<Menu> findByIdWithMenuInfo(Long id);
    Optional<Menu> findByName(String menuName);
}
