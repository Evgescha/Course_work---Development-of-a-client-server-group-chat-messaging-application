package com.hescha.chat.service;

import com.hescha.chat.model.Chat;
import com.hescha.chat.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService extends AbstractService<Chat> {
    private final ChatRepository repository;

    public ChatService(ChatRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Optional<Chat> findByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }
}
