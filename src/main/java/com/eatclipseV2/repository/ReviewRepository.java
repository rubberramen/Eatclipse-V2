package com.eatclipseV2.repository;

import com.eatclipseV2.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select r from Review r order by r.id desc")
    List<Review> findAllOrder();

    List<Review> findByShopId(Long shopId);

    @Query(value = "select r from Review r where r.shop.id = :shopId order by r.id desc")
    List<Review> findByShopIdOrder(@Param("shopId") Long shopId);

}
