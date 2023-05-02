package com.eatclipseV2.repository;

import com.eatclipseV2.entity.CartMenu;
import com.eatclipseV2.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {

    void delete(CartMenu cartmenu);

    void deleteById(Long id);

}
