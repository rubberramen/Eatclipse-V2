package com.eatclipseV2;

import com.eatclipseV2.entity.Address;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.entity.enums.Role;
import com.eatclipseV2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Address addressMember = new Address("부산", "수영로", "389");
        memberRepository.save(new Member("Wang", "rubberramen", "1234", addressMember, Role.USER));

        Address addressAdmin = new Address("우주", "은하수", "000");
        memberRepository.save(new Member("Admin", "admin", "1234", addressAdmin, Role.ADMIN));

    }
}
