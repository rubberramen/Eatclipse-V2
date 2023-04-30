package com.eatclipseV2.repository;

import com.eatclipseV2.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByOrderId(Long orderId);

}
