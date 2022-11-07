package com.hescha.chat.service;

import com.hescha.chat.model.User;
import com.hescha.chat.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User> {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
