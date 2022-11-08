package com.hescha.chat.controller;

import com.hescha.chat.domen.ChatAvatar;
import com.hescha.chat.domen.UserAvatar;
import com.hescha.chat.model.Chat;
import com.hescha.chat.model.Message;
import com.hescha.chat.model.User;
import com.hescha.chat.service.ChatService;
import com.hescha.chat.service.MessageService;
import com.hescha.chat.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final MessageService messageService;
    private final UserService userService;

    @GetMapping
    public String getChats(Model model) {
        model.addAttribute("pageName", "All chats");
        model.addAttribute("list", chatService.read());
        return "index.html";
    }

    @GetMapping("/my")
    public String getMyChats(Model model) {
        model.addAttribute("pageName", "My chats");
        return "index.html";
    }

    @GetMapping("/new")
    public String getNewChat(Model model) {
        model.addAttribute("pageName", "Creating new chat");
        model.addAttribute("entity", new Chat());
        return "chatEdit.html";
    }

    @GetMapping("/{id}")
    public String getChat(@PathVariable @NonNull Long id,
                          Model model) {
        Optional<Chat> byId = chatService.findById(id.intValue());
        if (byId.isEmpty()) {
            model.addAttribute("message", "Chat with id " + id + " does not found");
        }

        model.addAttribute("entity", byId.get());
        return "chat.html";
    }

    @GetMapping("/{id}/edit")
    public String editChat(@PathVariable @NonNull Long id,
                           Model model) {
        Optional<Chat> byId = chatService.findById(id.intValue());
        if (byId.isEmpty()) {
            model.addAttribute("message", "Chat with id " + id + " does not found");
            return "chat.html";
        }

        model.addAttribute("entity", byId.get());
        return "chatEdit.html";
    }

    @PostMapping
    public String saveOrUpdateChat(@ModelAttribute @NonNull Chat chat,
                                   @RequestParam @NonNull Integer avatars,
                                   RedirectAttributes redirectAttributes) {
        Chat current = null;
        Optional<Chat> byName = chatService.findByName(chat.getName());
        chat.setAvatar(ChatAvatar.findByNumber(avatars));

        if (byName.isPresent() && !byName.get().getId().equals(chat.getId())) {
            redirectAttributes.addAttribute("message", "Chat with same name already exists");
            redirectAttributes.addAttribute("entity", chat);
            return "chatEdit.html";
        } else {
            current = chatService.update(chat);
        }

        return "redirect:/chats/" + current.getId();
    }

    @PostMapping("/messages")
    public ResponseEntity<Void> saveOrUpdateChat(@RequestParam Long chat,
                                 @RequestParam String text,
                                 Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        Chat chat1 = chatService.findById(chat.intValue()).get();
        Message message = new Message();
        message.setChat(chat1);
        message.setOwner(user);
        message.setText(text);

        messageService.create(message);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> saveOrUpdateChat(@RequestParam Long chat,
                                          @RequestParam Long message) {
        Chat chat1 = chatService.findById(chat.intValue()).get();
        List<Message> newMessages = messageService.getNewMessages(chat1, message);
        return ResponseEntity.ok(newMessages);
    }
}
