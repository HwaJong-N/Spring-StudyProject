package com.ghkwhd.shop.controller.user;

import com.ghkwhd.shop.domain.user.User;
import com.ghkwhd.shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")   // admin 으로 시작하는 요청들을 처리
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/members")
    public String loadMembers(Model model) {
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("users", allUsers);
        return "admin/members";
    }
}
