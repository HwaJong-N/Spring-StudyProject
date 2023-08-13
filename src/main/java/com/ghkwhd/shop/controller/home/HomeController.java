package com.ghkwhd.shop.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home/home";
    }

    @GetMapping("/userHome")
    public String userHome() {
        return "home/userHome";
    }
}
