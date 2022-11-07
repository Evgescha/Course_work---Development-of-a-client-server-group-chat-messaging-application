package com.hescha.chat.controller;

import com.hescha.chat.model.User;
import com.hescha.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginAndRegistrationController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute User user,
                               Model model) {
        Optional<User> byUsername = userService.findByUsername(user.getUsername());
        if (byUsername.isPresent()) {
            model.addAttribute("message", "User with same username already exists");
            return "/login.html";
        } else {
            userService.create(user);
        }
        return "redirect:/";
    }

}
