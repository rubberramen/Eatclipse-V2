package com.eatclipseV2.controller;

import com.eatclipseV2.common.MessageDto;
import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.entity.Cart;
import com.eatclipseV2.entity.CartMenu;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.entity.Menu;
import com.eatclipseV2.service.CartService;
import com.eatclipseV2.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final MenuService menuService;
    private final CartService cartService;

    @PostMapping
    public String addCart(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                          @RequestParam(required = false) Long id, @RequestParam(required = false) Integer count,
                          Model model) {
        model.addAttribute("member", loginMember);

        Menu menu = menuService.findMenuById(id);
        CartMenu cartMenu = CartMenu.createCartMenu(menu, count);

        Cart cart;

        if (cartService.findCartByMemberId(loginMember.getId()) == null) {
            cart = Cart.createCart(loginMember, cartMenu, menu.getShop());
            cartService.saveCart(cart, cartMenu);
        } else {
            cart = cartService.findCartByMemberId(loginMember.getId());
            cartService.saveCartMenu(cart, cartMenu);
        }

        Long shopId = cart.getShop().getId();
        MessageDto messageDto = new MessageDto("장바구니에 담겼습니다",
                "/menus/shop/" + shopId, RequestMethod.GET, null);   // TODO: 2023-05-02 002 카트가 아니라 메뉴로 가야함

        return showMessageAndRedirect(messageDto, model);
    }

    @GetMapping("/{memberId}")
    public String showCart(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                           @PathVariable Long memberId,
                           Model model) {
        model.addAttribute("member", loginMember);

        Cart cart = cartService.findCartByMemberId(loginMember.getId());

        if (cart != null) {
            int totalAmount = cartService.getTotalAmount(cart);
            model.addAttribute("cart", cart);
            model.addAttribute("totalAmount", totalAmount);

            return "members/cart";
        } else {
            MessageDto messageDto = new MessageDto("장바구니가 비어있습니다!",
                    "/", RequestMethod.GET, null);

            return showMessageAndRedirect(messageDto, model);
        }
    }

    @PostMapping("/delete")  // TODO: 2023-05-02 002 URL 및 메서드 변경
    public String deleteCartMenu(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                                 @RequestParam(required = false) String menuId,
                                 Model model) {

        model.addAttribute("member", loginMember);

        Long integer = Long.valueOf(menuId);
        Menu menu = menuService.findMenuById(integer);
        cartService.deleteCartMenu(loginMember.getId(), menu);

        Cart cart = cartService.findCartByMemberId(loginMember.getId());

        if (cart.getCartMenus().isEmpty()) {
            cartService.deleteCart(cart);
            MessageDto messageDto = new MessageDto("해당 메뉴를 삭제했습니다.\n장바구니가 비었습니다.",
                    "/", RequestMethod.GET, null);

            return showMessageAndRedirect(messageDto, model);
        } else {
            MessageDto messageDto = new MessageDto("해당 메뉴를 삭제했습니다",
                    "/cart/" + loginMember.getId(), RequestMethod.GET, null);

            return showMessageAndRedirect(messageDto, model);
        }
    }

    private String showMessageAndRedirect(final MessageDto messageDto, Model model) {
        model.addAttribute("params", messageDto);
        return "common/messageRedirect";
    }
}
