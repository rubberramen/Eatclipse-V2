package com.eatclipseV2.controller;

import com.eatclipseV2.common.StringConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String main(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            if (session.getAttribute(StringConst.LOGIN_MEMBER) == null) {
                return "main";
            } else return "main-login";
        } else return "main";

    }
}
