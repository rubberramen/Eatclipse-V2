package com.eatclipseV2.controller;

import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.entity.*;
import com.eatclipseV2.service.MenuService;
import com.eatclipseV2.service.OrderService;
import com.eatclipseV2.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final MenuService menuService;
    private final ShopService shopService;
    private final OrderService orderService;

    @GetMapping
    public String main(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "main";
        }

        // 고객
        if (session.getAttribute(StringConst.LOGIN_MEMBER) != null) {
            Member member = (Member) session.getAttribute(StringConst.LOGIN_MEMBER);
            model.addAttribute("member", member);

            List<Shop> shops = shopService.findAllShop();
            model.addAttribute("shops", shops);

            return "main-memberLogin";
        }

        // 식당
        if (session.getAttribute(StringConst.LOGIN_SHOP) != null) {
            Shop shop = (Shop) session.getAttribute(StringConst.LOGIN_SHOP);
            model.addAttribute("shop", shop);

            List<Menu> menus = menuService.findMenusByShopId(shop.getId());
            model.addAttribute("menus", menus);

            return "main-shopLogin";
        }

        // 라이더
        if (session.getAttribute(StringConst.LOGIN_RIDER) != null) {
            Rider rider = (Rider) session.getAttribute(StringConst.LOGIN_RIDER);
            model.addAttribute("rider", rider);

            List<Order> orders = orderService.findAcceptedOrder();
            model.addAttribute("orders", orders);

            return "main-riderLogin";
        }

        return "main";
    }
}
