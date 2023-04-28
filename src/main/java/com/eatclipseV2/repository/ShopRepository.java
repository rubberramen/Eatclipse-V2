package com.eatclipseV2.repository;

import com.eatclipseV2.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findByName(String name);
}
