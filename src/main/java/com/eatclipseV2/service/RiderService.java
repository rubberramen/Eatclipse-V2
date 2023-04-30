package com.eatclipseV2.service;

import com.eatclipseV2.entity.Order;
import com.eatclipseV2.entity.Rider;
import com.eatclipseV2.entity.enums.DeliveryStatus;
import com.eatclipseV2.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RiderService {

    private final RiderRepository riderRepository;
    private final OrderService orderService;

    public Rider loginRider(String nickName, String password) {
        Rider rider = riderRepository.findByNickName(nickName);
        if (rider == null) {
            throw new IllegalStateException("라이더가 존재하지 않습니다");
        } else {
            if (rider.getPassword().equals(password)) {
                return rider;
            } else {
                throw new IllegalStateException("비밀번호가 일치하지 않습니다");
            }
        }
    }

    public void acceptDelivery(Long orderId, Long riderId) {
        Order order = orderService.findOrderByOrderId(orderId);
        Rider rider = riderRepository.findById(riderId).get();

        order.getDelivery().setRider(rider);
        order.getDelivery().setDeliveryStatus(DeliveryStatus.ON_GOING);
    }

}
