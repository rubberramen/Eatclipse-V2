package com.eatclipseV2.entity;

import com.eatclipseV2.entity.enums.MenuSellStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menu_id")
    private Long id;

    private String name;

    private int price;

    private String menuDtl;

    @Enumerated
    private MenuSellStatus menuSellStatus;
}
