package com.eatclipseV2.controller;

import com.eatclipseV2.common.MessageDto;
import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.domain.shop.dto.ShopLoginFormDto;
import com.eatclipseV2.entity.Menu;
import com.eatclipseV2.entity.Shop;
import com.eatclipseV2.service.MenuService;
import com.eatclipseV2.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;
    private final MenuService menuService;

    @GetMapping("/menus")
    public String menus(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                        Model model) {
        model.addAttribute("shop", loginShop);

        List<Menu> menus = menuService.findMenusByShopId(loginShop.getId());
        model.addAttribute("menus", menus);

        return "shops/menuList";
    }

    @GetMapping("/login")
    public String shopLogin(@ModelAttribute ShopLoginFormDto shopLoginFormDto) {
        return "shops/shopLoginForm";
    }

    @PostMapping("/login")
    public String shopLogin(@Valid ShopLoginFormDto shopLoginFormDto, BindingResult bindingResult,
                            HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
            return "shops/shopLoginForm";
        }

        try {
            Shop shop = shopService.loginShop(shopLoginFormDto.getName(), shopLoginFormDto.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute(StringConst.LOGIN_SHOP, shop);
            MessageDto messageDto = new MessageDto("로그인이 완료되었습니다", "/", RequestMethod.GET, null);
            return showMessageAndRedirect(messageDto, model);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "shops/shopLoginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/";
    }

    private String showMessageAndRedirect(final MessageDto messageDto, Model model) {
        model.addAttribute("params", messageDto);
        return "common/messageRedirect";
    }
}
