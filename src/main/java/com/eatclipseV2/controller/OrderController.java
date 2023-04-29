package com.eatclipseV2.controller;

import com.eatclipseV2.common.MessageDto;
import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.entity.*;
import com.eatclipseV2.entity.enums.OrderStatus;
import com.eatclipseV2.service.MenuService;
import com.eatclipseV2.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final MenuService menuService;
    private final OrderService orderService;

    @GetMapping("/{menuId}")
    public String menuDtlByMember(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                                  @PathVariable Long menuId, Model model) {
        model.addAttribute("member", loginMember);
        Menu menu = menuService.menuDtl(menuId);
        Shop shop = menu.getShop();
        model.addAttribute("shop", shop);
        model.addAttribute("menu", menu);

        return "menus/menuDtl";
    }

    @PostMapping
    public String order(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                        Model model, @RequestParam int count, @RequestParam("id") Long menuId,
                        @RequestParam("shopId") Long shopId) {
        model.addAttribute("member", loginMember);

        orderService.order(loginMember.getId(), shopId, menuId, count);

        MessageDto messageDto = new MessageDto("주문이 되었습니다. 식당에서 접수할 예정입니다.",
                "/order/member/list", RequestMethod.GET, null);

        return showMessageAndRedirect(messageDto, model);
    }

    @GetMapping("/member/list")
    public String orderList(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                            Model model) {
        model.addAttribute("member", loginMember);
        List<Order> orders = orderService.orderListByMember(loginMember.getId());
        model.addAttribute("orders", orders);
        return "order/orderListByMember";
    }

    @GetMapping("/shop/list")
    public String orderList(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                            Model model) {
        model.addAttribute("shop", loginShop);
        List<Order> orders = orderService.orderListByShop(loginShop.getId());
        model.addAttribute("orders", orders);
        return "order/orderListByShop";
    }

    @GetMapping("/shop/{orderId}")
    public String orderDtlByShop(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                                 Model model, @PathVariable Long orderId) {

        model.addAttribute("shop", loginShop);
        Order order = orderService.orderByOrderId(orderId);
        int totalPrice = order.getTotalPrice();
        model.addAttribute("order", order);
        model.addAttribute("totalPrice", totalPrice);

        // TODO: 2023-04-29 029 Cart 기능 시 수정 예정
        OrderMenu orderMenu = order.getOrderMenus().get(0);
        model.addAttribute("orderMenu", orderMenu);
        return "order/orderDtlByShop";
    }

    @GetMapping("/shop/{orderId}/response")
    public String shopResponse(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                      Model model, @PathVariable Long orderId, @RequestParam String orderStatus) {
        model.addAttribute("shop", loginShop);
        orderService.shopResponse(orderId, orderStatus);

        return "redirect:/order/shop/list";
    }














    @GetMapping("/member/{orderId}")
    public String orderDtlByShop(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                                 Model model, @PathVariable Long orderId) {

        model.addAttribute("member", loginMember);
        Order order = orderService.orderByOrderId(orderId);
        int totalPrice = order.getTotalPrice();
        model.addAttribute("order", order);
        model.addAttribute("totalPrice", totalPrice);

        // TODO: 2023-04-29 029 Cart 기능 시 수정 예정
        OrderMenu orderMenu = order.getOrderMenus().get(0);
        model.addAttribute("orderMenu", orderMenu);
        return "order/orderDtlByMember";


    }















    private String showMessageAndRedirect(final MessageDto messageDto, Model model) {
        model.addAttribute("params", messageDto);
        return "common/messageRedirect";
    }
}
