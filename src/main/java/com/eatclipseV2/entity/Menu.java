package com.eatclipseV2.entity;

import com.eatclipseV2.entity.enums.MenuSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menu_id")
    private Long id;

    private String name;

    private int price;

    private String menuDtl;

    @Enumerated(EnumType.STRING)
    private MenuSellStatus menuSellStatus;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public Menu() {
    }

    public Menu(String name, int price, String menuDtl, MenuSellStatus menuSellStatus, Shop shop) {
        this.name = name;
        this.price = price;
        this.menuDtl = menuDtl;
        this.menuSellStatus = menuSellStatus;
        this.shop = shop;
    }
}
