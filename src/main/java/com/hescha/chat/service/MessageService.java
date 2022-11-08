package com.hescha.chat.service;

import com.hescha.chat.model.Chat;
import com.hescha.chat.model.Message;
import com.hescha.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService extends AbstractService<Message> {
    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Message> getNewMessages(Chat chat, Long lastMessageId){
        return repository.findByChatAndIdGreaterThan(chat, lastMessageId);
    }
}
