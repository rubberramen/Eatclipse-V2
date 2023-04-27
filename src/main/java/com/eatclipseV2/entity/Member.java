package com.eatclipseV2.entity;

import com.eatclipseV2.domain.member.dto.MemberFormDto;
import com.eatclipseV2.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String nickName;

    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int cash;

//    @OneToMany
//    private List<Order> orders;

    public static Member createMember(MemberFormDto memberFormDto) {

        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setNickName(memberFormDto.getNickName());
        member.setPassword(memberFormDto.getPassword());
        member.setAddress(new Address(memberFormDto.getCity(), memberFormDto.getStreet(), memberFormDto.getZipcode()));
        member.setRole(Role.USER);
        member.setCash(0);

        return member;
    }

    public Member() {
    }

    public Member(String name, String nickName, String password, Address address, Role role, int cash) {
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.address = address;
        this.role = role;
        this.cash = cash;
    }
}
