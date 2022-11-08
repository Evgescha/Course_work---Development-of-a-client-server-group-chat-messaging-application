package com.hescha.chat.controller;

import com.hescha.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
    private final ChatService chatService;

    @GetMapping(path = {"/", "/chats"})
    public String getChats(Model model) {
        model.addAttribute("pageName", "All chats");
        return "index.html";
    }

    @GetMapping("/chats/my")
    public String getMyChats(Model model) {
        model.addAttribute("pageName", "My chats");
        return "index.html";
    }

    @GetMapping("/chats/new")
    public String getNewChat(Model model) {
        model.addAttribute("pageName", "Creating new chat");
        return "index.html";
    }
}
