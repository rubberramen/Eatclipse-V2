package com.eatclipseV2.entity;

import com.eatclipseV2.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = LAZY)
    private Delivery delivery;

    // 연관 관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void setShop(Shop shop) {
        this.shop = shop;
        shop.getOrders().add(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenus.add(orderMenu);
        orderMenu.setOrder(this);
    }

    // TODO: 2023-04-29 029 카트 기능 추가시 로직 수정 예정
    public static Order createOrder(Member member, Shop shop, List<OrderMenu> orderMenus) {
        Order order = new Order();
        order.setMember(member);
        order.setShop(shop);
        order.setOrderStatus(OrderStatus.ON_GOING);

        // Delivery 생성
        Delivery delivery = Delivery.createDelivery(order);
        order.setDelivery(delivery);

        for (OrderMenu orderMenu : orderMenus) {
            order.addOrderMenu(orderMenu);
        }

        return order;
    }

    public int getOrderPrice() {
        int orderPrice = 0;

        for (OrderMenu orderMenu : orderMenus) {
            orderPrice += orderMenu.getCount() * orderMenu.getOrderPrice();
        }

        return orderPrice;
    }

    public int getTotalPrice() {
        int orderPrice = getOrderPrice();
        int deliveryPrice = 3000;
        return orderPrice + deliveryPrice;
    }
}
