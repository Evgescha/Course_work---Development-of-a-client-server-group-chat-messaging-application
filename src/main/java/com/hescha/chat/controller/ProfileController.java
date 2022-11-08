package com.hescha.chat.controller;

import com.hescha.chat.domen.UserAvatar;
import com.hescha.chat.model.User;
import com.hescha.chat.service.ChatService;
import com.hescha.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ChatService chatService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String getProfile(Model model, Principal principal) {
        Optional<User> byUsername = userService.findByUsername(principal.getName());

        model.addAttribute("pageName", "My profile");
        model.addAttribute("entity", byUsername.get());
        return "profile.html";
    }

    @PostMapping
    public String registerUser(@ModelAttribute User user,
                               @RequestParam Integer avatars,
                               RedirectAttributes redirectAttributes) {
        Optional<User> byUsername = userService.findByUsername(user.getUsername());
        if (byUsername.isPresent() && !byUsername.get().getId().equals(user.getId())) {
            redirectAttributes.addAttribute("message", "User with same username already exists");
        } else {
            User saved = byUsername.get();
            saved.setPassword(passwordEncoder.encode(user.getPassword()));
            saved.setAvatar(UserAvatar.findByNumber(avatars));
            saved.setEmail(user.getEmail());
            saved.setUsername(user.getUsername());
            saved.setBirthDate(user.getBirthDate());
            userService.update(saved);
        }
        return "redirect:/profile";
    }
}
