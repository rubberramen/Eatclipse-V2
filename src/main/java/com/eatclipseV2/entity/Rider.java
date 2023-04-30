package com.eatclipseV2.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rider_id")
    private Long id;

    private String name;

    private String nickName;

    private String password;

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL)
    private List<Delivery> deliveryList = new ArrayList<>();

    public Rider() {
    }

    public Rider(String name, String nickName, String password) {
        this.name = name;
        this.nickName = nickName;
        this.password = password;
    }
}
