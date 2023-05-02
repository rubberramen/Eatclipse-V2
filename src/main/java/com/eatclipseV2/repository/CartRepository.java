package com.eatclipseV2.repository;

import com.eatclipseV2.entity.Cart;
import com.eatclipseV2.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);
}
