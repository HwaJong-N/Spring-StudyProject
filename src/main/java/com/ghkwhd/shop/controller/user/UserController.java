package com.ghkwhd.shop.controller.user;

import com.ghkwhd.shop.domain.user.User;
import com.ghkwhd.shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signUp")
    public String loadSignUp(Model model) {
        model.addAttribute("user", new User());
        return "signUp/signUpForm";
    }

    @PostMapping("/signUp")
    public String addUser(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            return "signUp/signUpForm";
        }

        if (userService.isExistId(user.getId())) {
            bindingResult.rejectValue("id", "duplicate");
            return "signUp/signUpForm";
        }


        userService.save(user);
        return "home";
    }
}
