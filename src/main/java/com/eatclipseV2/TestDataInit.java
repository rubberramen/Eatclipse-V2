package com.eatclipseV2;

import com.eatclipseV2.entity.Address;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.entity.Shop;
import com.eatclipseV2.entity.enums.Category;
import com.eatclipseV2.entity.enums.Role;
import com.eatclipseV2.repository.MemberRepository;
import com.eatclipseV2.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;

    @PostConstruct
    public void init() {
        // 고객 데이터
        Address addressMember = new Address("부산", "수영로", "389");
        memberRepository.save(new Member("Wang", "rubberramen", "1234", addressMember, Role.USER, 10000));

        // Admin 데이터
        Address addressAdmin = new Address("우주", "은하수", "000");
        memberRepository.save(new Member("Admin", "admin", "1234", addressAdmin, Role.ADMIN, 10000));

        // 식당 데이터
        Address addressKorean = new Address("부산", "한식", "000");
        shopRepository.save(new Shop("한식", "1234", addressKorean, Category.KOREAN));

        Address addressBBQ = new Address("부산", "구이", "000");
        shopRepository.save(new Shop("구이", "1234", addressBBQ, Category.BBQ));

        Address addressChinese = new Address("부산", "중식", "000");
        shopRepository.save(new Shop("중식", "1234", addressChinese, Category.CHINESE));

        Address addressJapanese = new Address("부산", "일식", "000");
        shopRepository.save(new Shop("일식", "1234", addressJapanese, Category.JAPANESE));

        Address addressWestern = new Address("부산", "양식", "000");
        shopRepository.save(new Shop("양식", "1234", addressWestern, Category.WESTERN));

        Address addressRaw = new Address("부산", "회", "000");
        shopRepository.save(new Shop("회", "1234", addressRaw, Category.RAW));

        Address addressCafe = new Address("부산", "카페", "000");
        shopRepository.save(new Shop("카페", "1234", addressCafe, Category.CAFE));

        Address addressEtc = new Address("부산", "기타", "000");
        shopRepository.save(new Shop("기타", "1234", addressKorean, Category.ETC));




    }
}
