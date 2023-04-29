package com.eatclipseV2.service;

import com.eatclipseV2.entity.*;
import com.eatclipseV2.entity.enums.OrderStatus;
import com.eatclipseV2.repository.MemberRepository;
import com.eatclipseV2.repository.MenuRepository;
import com.eatclipseV2.repository.OrderRepository;
import com.eatclipseV2.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    
    public Long makeOrder(Long memberId, Long shopId, Long menuId, int count) {

        // 엔티티 조회 : 멤버, 식당, 메뉴
        Member member = memberRepository.findById(memberId).get();
        Shop shop = shopRepository.findById(shopId).get();
        Menu menu = menuRepository.findById(menuId).get();

        // 주문 메뉴 생성
        OrderMenu orderMenu = OrderMenu.createOrderMenu(menu, count);

        // 주문 생성
        Order order = Order.createOrder(member, shop, orderMenu);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    public List<Order> orderListByMember(Long memberId) {
        return orderRepository.findByMemberIdOrderByIdDesc(memberId);
    }

    public List<Order> orderListByShop(Long shopId) {
        return orderRepository.findByShopIdOrderByIdDesc(shopId);
    }

    public Order findOrderByOrderId(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    public void shopResponse(Long orderId, String orderStatus) {
        Order order = findOrderByOrderId(orderId);
        int totalPrice = order.getTotalPrice();

        if (orderStatus.equals("accept")) {
            order.setOrderStatus(OrderStatus.COMPLETE);
            order.getMember().minusCash(totalPrice);
        } else {
            order.setOrderStatus(OrderStatus.CANCLE);
        }
    }
}
