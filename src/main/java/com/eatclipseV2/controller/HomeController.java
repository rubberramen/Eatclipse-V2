package com.eatclipseV2.controller;

import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.entity.Shop;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String main(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "main";
        }

        if (session.getAttribute(StringConst.LOGIN_SHOP) != null) {
            Shop shop = (Shop) session.getAttribute(StringConst.LOGIN_SHOP);
            model.addAttribute("shop", shop);
            return "main-shopLogin";
        }

        if (session.getAttribute(StringConst.LOGIN_MEMBER) != null) {
            Member member = (Member) session.getAttribute(StringConst.LOGIN_MEMBER);
            model.addAttribute("member", member);
            return "main-memberLogin";
        }

        return "main";
    }



//    @GetMapping
//    public String main(HttpServletRequest request, Model model) {
//
//        HttpSession session = request.getSession(false);
//
//        if (session != null) {
//
//            if (session.getAttribute(StringConst.LOGIN_SHOP) == null) {
//                return "main";
//            } else if (session.getAttribute(StringConst.LOGIN_SHOP) != null){
//                Shop shop = (Shop) session.getAttribute(StringConst.LOGIN_SHOP);
//                model.addAttribute("shop", shop);
//                return "main-shopLogin";
//            }
//
//            if (session.getAttribute(StringConst.LOGIN_MEMBER) == null) {
//                return "main";
//            } else if (session.getAttribute(StringConst.LOGIN_MEMBER) != null) {
//                Member member = (Member) session.getAttribute(StringConst.LOGIN_MEMBER);
//                model.addAttribute("member", member);
//                return "main-memberLogin";
//            }
//
//        } else return "main";
//
//        return "main";
//    }
}
