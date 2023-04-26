package com.eatclipseV2.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test01")
    public String test01() {
        return "main";
    }
}