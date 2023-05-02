package com.eatclipseV2.repository;

import com.eatclipseV2.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartMenuRepositoryTest {

    @Autowired
    CartMenuRepository cartMenuRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    CartRepository cartRepository;

    @Test
    public void test() throws Exception {
        // given

        Member member = memberRepository.findById(1L).get();
        Menu menu = menuRepository.findById(5L).get();
        Shop shop = shopRepository.findById(4L).get();

        CartMenu cartMenu = CartMenu.createCartMenu(menu, 3);

        Cart cart = Cart.createCart(member, cartMenu, shop);
        cartRepository.save(cart);


        cartMenuRepository.deleteById(cart.getCartMenus().get(0).getId());



        // when

        // then
    }


}