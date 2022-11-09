package com.hescha.chat.service;

import com.hescha.chat.model.Chat;
import com.hescha.chat.model.User;
import com.hescha.chat.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService extends AbstractService<Chat> {
    private final ChatRepository repository;
    private final MessageService messageService;

    public ChatService(ChatRepository repository,
                       MessageService messageService) {
        super(repository);
        this.repository = repository;
        this.messageService = messageService;
    }

    public Optional<Chat> findByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }

    public List<Chat> findByOwner(User owner) {
        return repository.findByOwner(owner);
    }

    public List<Chat> findByUsersContains(User user) {
        return repository.findByUsersContains(user);
    }

    @Override
    public void delete(int id) {
        Chat chat = repository.getById((long) id);

        chat.getUsers().forEach(user -> user.getChats().remove(chat));
        chat.getUsers().clear();
        chat.getMessages().forEach(message ->messageService.delete(message.getId().intValue()));
        repository.save(chat);

        repository.deleteById((long) id);
    }
}
