package com.ghkwhd.shop.controller.error;

import com.ghkwhd.shop.domain.user.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {
    @GetMapping("/accessDenied")
    public String accessDenied(@RequestParam(value="exception") String msg,
                               @RequestParam(value="status") String status,
                               Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("username", principal.getUsername());
        // @RequestParam 으로 전달받은 에러 메세지를 model 에 담는다
        model.addAttribute("status", status);
        model.addAttribute("msg", msg);
        return "/error/accessDenied";
    }
}
