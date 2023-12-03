package com.kjj.menu.repository;

import com.kjj.menu.entity.MenuFood;
import com.kjj.menu.entity.MenuInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuInfoRepository extends JpaRepository<MenuInfo, Long> {
}
