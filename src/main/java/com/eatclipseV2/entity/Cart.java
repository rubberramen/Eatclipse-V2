package com.eatclipseV2.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "cart")   // TODO: cascade = CascadeType.ALL 서로 다른 싸이클 일때 cascade 꼭 확인
    private List<CartMenu> cartMenus = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.setCart(this);
    }

    public static Cart createCart(Member member, CartMenu cartMenu, Shop shop) {
        Cart cart = new Cart();
        cart.setMember(member);
        cart.setShop(shop);
        cart.getCartMenus().add(cartMenu);
        return cart;
    }
}
