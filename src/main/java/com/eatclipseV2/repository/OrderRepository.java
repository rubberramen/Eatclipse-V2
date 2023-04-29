package com.eatclipseV2.repository;

import com.eatclipseV2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMemberId(Long memberId);
    List<Order> findByMemberIdOrderByIdDesc(Long memberId);


    List<Order> findByShopId(Long shopId);

    List<Order> findByShopIdOrderByMemberIdDesc(Long shopId);

    List<Order> findByShopIdOrderByIdDesc(Long shopId);

}
