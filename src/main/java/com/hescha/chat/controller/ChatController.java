package com.hescha.chat.controller;

import com.hescha.chat.domen.ChatAvatar;
import com.hescha.chat.model.Chat;
import com.hescha.chat.model.Message;
import com.hescha.chat.model.User;
import com.hescha.chat.service.ChatService;
import com.hescha.chat.service.MessageService;
import com.hescha.chat.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
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
        List<Chat> chatList = chatService.read();
        model.addAttribute("list", chatList);
        return "chats.html";
    }

    @GetMapping("/my")
    public String getMyChats(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("pageName", "My chats");

        List<Chat> whereOwner = chatService.findByOwner(user);
        List<Chat> whereIn = chatService.findByUsersContains(user);

        model.addAttribute("ownerList", whereOwner);
        model.addAttribute("myList", whereIn);
        return "myChats.html";
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
            return "index.html";
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
            return "index.html";
        }

        model.addAttribute("entity", byId.get());
        return "chatEdit.html";
    }


    @GetMapping("/{id}/delete")
    public String deleteChat(@PathVariable @NonNull Long id,
                           Principal principal,
                             Model model) {
        Optional<Chat> byId = chatService.findById(id.intValue());
        if (byId.isEmpty()) {
            model.addAttribute("message", "Chat with id " + id + " does not found");
            return "index.html";
        }

        User user = userService.findByUsername(principal.getName()).get();
        Chat chat = byId.get();
        if(!Objects.equals(user.getId(), chat.getOwner().getId())){
            model.addAttribute("message", "You are now owner do delete this chat");
            return "index.html";
        }

        chatService.delete(chat.getId().intValue());
        return "redirect:/chats/my";
    }

    @PostMapping
    public String saveOrUpdateChat(@ModelAttribute @NonNull Chat chat,
                                   @RequestParam @NonNull Integer avatars,
                                   RedirectAttributes redirectAttributes,
                                   Principal principal) {

        Chat current = null;
        User user = userService.findByUsername(principal.getName()).get();
        Optional<Chat> byName = chatService.findByName(chat.getName());
        chat.setAvatar(ChatAvatar.findByNumber(avatars));

        if (byName.isPresent() && !byName.get().getId().equals(chat.getId())) {
            redirectAttributes.addAttribute("message", "Chat with same name already exists");
            redirectAttributes.addAttribute("entity", chat);
            return "chatEdit.html";
        } else {
            chat.setOwner(user);
            current = chatService.update(chat);
            user.getChats().add(current);
            userService.update(user);
        }

        return "redirect:/chats/" + current.getId();
    }

    @PostMapping("/messages")
    public ResponseEntity<Void> postMessage(@RequestParam Long chat,
                                 @RequestParam String text,
                                 Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        Chat chat1 = chatService.findById(chat.intValue()).get();
        Message message = new Message();
        message.setChat(chat1);
        message.setOwner(user);
        message.setText(text);

        Message message1 = messageService.create(message);

        chat1.getMessages().add(message1);
        Chat update = chatService.update(chat1);
        user.getChats().add(update);
        userService.update(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getNewMessage(@RequestParam Long chat,
                                          @RequestParam Long message) {
        Chat chat1 = chatService.findById(chat.intValue()).get();
        List<Message> newMessages = messageService.getNewMessages(chat1, message);
        return ResponseEntity.ok(newMessages);
    }
}
