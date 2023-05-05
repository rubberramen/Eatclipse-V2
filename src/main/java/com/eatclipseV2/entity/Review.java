package com.eatclipseV2.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    private int deleteYn;

    private int viewCnt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public Review() {
    }

    public Review(String title, String content, int deleteYn, int viewCnt, Member member, Shop shop) {
        this.title = title;
        this.content = content;
        this.deleteYn = deleteYn;
        this.viewCnt = viewCnt;
        this.member = member;
        this.shop = shop;
    }
}
