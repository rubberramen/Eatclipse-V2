package com.eatclipseV2.entity;

import com.eatclipseV2.entity.enums.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id")
    private Rider rider;

    // 연관관계 편의 메서드
    public void setRider(Rider rider) {
        this.rider = rider;
        rider.getDeliveryList().add(this);
    }

    public static Delivery createDelivery(Order order) {
        Delivery delivery = new Delivery();

        delivery.setDeliveryStatus(DeliveryStatus.BEFORE);
        delivery.setOrder(order);

        return delivery;
    }
}

