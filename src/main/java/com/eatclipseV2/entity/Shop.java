package com.eatclipseV2.entity;

import com.eatclipseV2.entity.enums.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shop_id")
    private Long id;

    private String name;

    private String password;

    @Embedded
    private Address address;

    @Enumerated
    private Category category;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();

    public Shop() {
    }

    public Shop(String name, String password, Address address, Category category) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.category = category;
    }
}
