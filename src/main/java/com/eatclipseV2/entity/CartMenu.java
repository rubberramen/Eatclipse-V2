package com.eatclipseV2.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CartMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int count;
    private int amount;

    public static CartMenu createCartMenu(Menu menu, int count) {

        CartMenu cartMenu = new CartMenu();
        cartMenu.setMenu(menu);
        cartMenu.setCount(count);
        int amount = count * menu.getPrice();
        cartMenu.setAmount(amount);

        return cartMenu;
    }
}
