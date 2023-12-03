package com.kjj.menu.repository;

import com.kjj.menu.entity.Menu;
import com.kjj.menu.entity.MenuFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuFoodRepository extends JpaRepository<MenuFood, Long> {
}
