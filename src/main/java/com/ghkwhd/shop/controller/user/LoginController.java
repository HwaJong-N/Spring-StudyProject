package com.ghkwhd.shop.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class LoginController {
    @GetMapping("/loginHome")
    public String loginHome(@RequestParam(value = "exception", required = false) String msg, Model model) {
        model.addAttribute("msg", msg);
        return "home/loginHome";
    }
}
