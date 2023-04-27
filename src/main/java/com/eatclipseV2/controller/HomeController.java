package com.eatclipseV2.controller;

import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.entity.Member;
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

        if (session != null) {
            if (session.getAttribute(StringConst.LOGIN_MEMBER) == null) {
                return "main";
            } else {
                Member member = (Member) session.getAttribute(StringConst.LOGIN_MEMBER);
                model.addAttribute("member", member);
                return "main-login";
            }
        } else return "main";

    }
}
