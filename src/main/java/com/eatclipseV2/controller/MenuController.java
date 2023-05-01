package com.eatclipseV2.controller;

import com.eatclipseV2.common.MessageDto;
import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.domain.menu.dto.MenuFormDto;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.entity.Menu;
import com.eatclipseV2.entity.Shop;
import com.eatclipseV2.service.MenuService;
import com.eatclipseV2.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;
    private final ShopService shopService;

    @GetMapping("/new")
    public String addMenu(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                          @ModelAttribute MenuFormDto menuFormDto, Model model) {

        model.addAttribute("shop", loginShop);

        model.addAttribute("sell", "SELL");
        model.addAttribute("sold_out", "SOLD_OUT");
        return "menus/addMenuForm";
    }

    @PostMapping("/new")
    public String addMenu(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                          @Valid MenuFormDto menuFormDto, BindingResult bindingResult,
                          Model model) {

        model.addAttribute("shop", loginShop);

        if (bindingResult.hasErrors()) {
            model.addAttribute("sell", "SELL");
            model.addAttribute("sold_out", "SOLD_OUT");
            return "menus/addMenuForm";
        }

        menuService.addMenu(menuFormDto, loginShop.getId());

        MessageDto messageDto = new MessageDto("메뉴 등록이 완료되었습니다.",
                "/shops/menus", RequestMethod.GET, null);
        return showMessageAndRedirect(messageDto, model);
    }

    @GetMapping("/{menuId}/edit")
    public String menuDtl(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                          @PathVariable Long menuId, Model model) {

        Menu menu = menuService.menuDtl(menuId);
        MenuFormDto menuForm = MenuFormDto.of(menu);
        model.addAttribute("menuForm", menuForm);
        model.addAttribute("sell", "SELL");
        model.addAttribute("sold_out", "SOLD_OUT");
        return "/menus/menuDtlForm";
    }

    @PostMapping("/{menuId}/edit")
    public String menuDtl(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                          @Valid MenuFormDto menuFormDto, BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("sell", "SELL");
            model.addAttribute("sold_out", "SOLD_OUT");
            return "menus/addMenuForm";
        }

        menuService.updateMenu(loginShop.getId(), menuFormDto);

        MessageDto messageDto = new MessageDto("메뉴 수정이 완료되었습니다.",
                "/", RequestMethod.GET, null);

        return showMessageAndRedirect(messageDto, model);
    }

    @PostMapping("/{menuId}/delete")
    public String delete(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                         @PathVariable Long menuId, Model model) {
        menuService.deleteMenu(menuId);
        MessageDto messageDto = new MessageDto("메뉴 삭제가 완료되었습니다.",
                "/", RequestMethod.GET, null);
        return showMessageAndRedirect(messageDto, model);
    }

    @GetMapping("/shop/{shopId}")
    public String menus(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                          @PathVariable Long shopId, Model model) {

        model.addAttribute("member", loginMember);
        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", shop);

        List<Menu> menusByShopId = menuService.findMenusByShopId(shopId);
        model.addAttribute("menus", menusByShopId);
        return "members/menus";
    }

    private String showMessageAndRedirect(final MessageDto messageDto, Model model) {
        model.addAttribute("params", messageDto);
        return "common/messageRedirect";
    }
}
