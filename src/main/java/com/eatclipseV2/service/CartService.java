package com.eatclipseV2.service;

import com.eatclipseV2.entity.Cart;
import com.eatclipseV2.entity.CartMenu;
import com.eatclipseV2.entity.Menu;
import com.eatclipseV2.repository.CartMenuRepository;
import com.eatclipseV2.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;

    public Cart findCartByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId);
    }

    public void saveCart(Cart cart, CartMenu cartMenu) {
        cartRepository.save(cart);
        cartMenu.setCart(cart);
        cartMenuRepository.save(cartMenu);
    }

    public void saveCartMenu(Cart cart, CartMenu cartMenu) {
        Cart findCart = cartRepository.findById(cart.getId()).get();
        findCart.getCartMenus().add(cartMenu);
        cartMenu.setCart(cart);
        cartMenuRepository.save(cartMenu);
    }

    public int getTotalAmount(Cart cart) {

        int total = 0;

        List<CartMenu> cartMenus = cart.getCartMenus();
        for (CartMenu cartMenu : cartMenus) {
            total += cartMenu.getAmount();
        }
        return total;
    }

    // TODO: Need Refactoring
    public void deleteCartMenu(Long memberId, Menu menu) {

        Cart cart = cartRepository.findByMemberId(memberId);
        List<CartMenu> cartMenus = cart.getCartMenus();

        for (int i = 0; i < cartMenus.size(); i++) {

            if (cartMenus.get(i).getMenu().getId().equals(menu.getId())) {
                cartMenuRepository.deleteById(cart.getCartMenus().get(i).getId());
                cart.getCartMenus().remove(i);
            }
        }
    }

    public void deleteCartMenus(Long memberId) {

        Cart cart = cartRepository.findByMemberId(memberId);
        List<CartMenu> cartMenus = cart.getCartMenus();

        for (CartMenu cartMenu : cartMenus) {
            cartMenuRepository.deleteById(cartMenu.getId());
        }

        cart.setCartMenus(new ArrayList<>());
    }

    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }
}
