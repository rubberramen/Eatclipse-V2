package com.eatclipseV2;

import com.eatclipseV2.entity.*;
import com.eatclipseV2.entity.enums.Category;
import com.eatclipseV2.entity.enums.MenuSellStatus;
import com.eatclipseV2.entity.enums.Role;
import com.eatclipseV2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final RiderRepository riderRepository;
    private final ReviewRepository reviewRepository;

    @PostConstruct
    public void init() {
        // 고객 데이터
        Address addressMember = new Address("부산", "수영로", "389");
        Member member1 = new Member("Wang", "rubberramen", "1234", addressMember, Role.USER, 100000);
        memberRepository.save(member1);

        Address addressMember2 = new Address("부산", "절영로", "389");
        Member member2 = new Member("tester", "tester", "1234", addressMember, Role.USER, 10000);
        memberRepository.save(member2);

        // Admin 데이터
        Address addressAdmin = new Address("우주", "은하수", "000");
        memberRepository.save(new Member("Admin", "admin", "1234", addressAdmin, Role.ADMIN, 10000));

        // 한식식당 데이터
        Address addressKorean = new Address("부산", "한식로", "123");
        Shop koreanShop = new Shop("한식식당", "1234", addressKorean, Category.KOREAN);
        shopRepository.save(koreanShop);

        // 한식식당 메뉴 데이터
        menuRepository.save(new Menu("떡볶이", 5000, "국물 떡볶이", MenuSellStatus.SELL, koreanShop));
        menuRepository.save(new Menu("밀면", 7000, "부산 밀면", MenuSellStatus.SELL, koreanShop));
        menuRepository.save(new Menu("김치찌개", 8000, "돼지고기 김치찌개", MenuSellStatus.SELL, koreanShop));

        // 구이식당 데이터, 중식식당 메뉴 데이터
        Address addressBBQ = new Address("부산", "구이로", "462");
        Shop bbqShop = new Shop("구이식당", "1234", addressBBQ, Category.BBQ);
        shopRepository.save(bbqShop);
        menuRepository.save(new Menu("삼겹살 구이", 10000, "삼겹살", MenuSellStatus.SELL, bbqShop));
        menuRepository.save(new Menu("목살 구이", 10000, "목살", MenuSellStatus.SELL, bbqShop));
        menuRepository.save(new Menu("항정살", 12000, "항정살", MenuSellStatus.SELL, bbqShop));

        // 기타 식당 데이터
        Address addressChinese = new Address("부산", "중식", "000");
        shopRepository.save(new Shop("중식식당", "1234", addressChinese, Category.CHINESE));

        Address addressJapanese = new Address("부산", "일식", "000");
        shopRepository.save(new Shop("일식식당", "1234", addressJapanese, Category.JAPANESE));

        Address addressWestern = new Address("부산", "양식", "000");
        shopRepository.save(new Shop("양식식당", "1234", addressWestern, Category.WESTERN));

        Address addressRaw = new Address("부산", "회", "000");
        shopRepository.save(new Shop("회식당", "1234", addressRaw, Category.RAW));

        Address addressCafe = new Address("부산", "카페", "000");
        shopRepository.save(new Shop("카페식당", "1234", addressCafe, Category.CAFE));

        Address addressEtc = new Address("부산", "기타", "000");
        shopRepository.save(new Shop("기타식당", "1234", addressKorean, Category.ETC));

        // 라이더 데이터
        riderRepository.save(new Rider("라이더1", "rider1", "1234"));
        riderRepository.save(new Rider("라이더2", "rider2", "1234"));

        // 리뷰 데이터
        Review review1 = new Review("한식식당 맛나요", "정말 맛나요", 0, 0, member1, koreanShop);
        reviewRepository.save(review1);

        Review review2 = new Review("구이식당 맛나요", "정말 맛나요", 0, 0, member2, bbqShop);
        reviewRepository.save(review2);
    }
}
