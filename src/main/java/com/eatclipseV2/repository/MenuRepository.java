package com.eatclipseV2.repository;

import com.eatclipseV2.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findMenusByShopId(Long shopId);

    List<Menu> findMenuByName(String menuName);
}
