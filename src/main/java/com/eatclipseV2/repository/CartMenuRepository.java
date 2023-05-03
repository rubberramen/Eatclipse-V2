package com.eatclipseV2.repository;

import com.eatclipseV2.entity.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {

    void delete(CartMenu cartmenu);

    void deleteById(Long id);

    List<CartMenu> findByCartId(Long cartId);

}
