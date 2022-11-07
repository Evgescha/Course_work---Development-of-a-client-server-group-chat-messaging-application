package com.hescha.chat.service;

import com.hescha.chat.model.Message;
import com.hescha.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends AbstractService<Message> {
    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
