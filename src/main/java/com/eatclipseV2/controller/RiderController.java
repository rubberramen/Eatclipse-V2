package com.eatclipseV2.controller;

import com.eatclipseV2.common.MessageDto;
import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.domain.rider.dto.RiderLoginFormDto;
import com.eatclipseV2.domain.shop.dto.ShopLoginFormDto;
import com.eatclipseV2.entity.Rider;
import com.eatclipseV2.entity.Shop;
import com.eatclipseV2.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/riders")
public class RiderController {

    private final RiderService riderService;

    @GetMapping("/{orderId}")
    public String acceptOrder(@SessionAttribute(name = StringConst.LOGIN_RIDER) Rider loginRider,
                      @PathVariable Long orderId, Model model) {

        model.addAttribute("rider", loginRider);

        // 해당 주문 배달 수락
        riderService.acceptDelivery(orderId, loginRider.getId());

        MessageDto messageDto = new MessageDto("해당 배달이 수락되었습니다.",
                "/", RequestMethod.GET, null);

        return showMessageAndRedirect(messageDto, model);
    }

    @GetMapping("/login")
    public String shopLogin(@ModelAttribute RiderLoginFormDto riderLoginFormDto) {
        return "riders/riderLoginForm";
    }

    @PostMapping("/login")
    public String shopLogin(@Valid RiderLoginFormDto riderLoginFormDto, BindingResult bindingResult,
                            HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
            return "riders/riderLoginForm";
        }

        try {
            Rider rider = riderService.loginRider(riderLoginFormDto.getNickName(), riderLoginFormDto.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute(StringConst.LOGIN_RIDER, rider);
            MessageDto messageDto = new MessageDto("로그인이 완료되었습니다", "/", RequestMethod.GET, null);
            return showMessageAndRedirect(messageDto, model);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "riders/riderLoginForm";
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
