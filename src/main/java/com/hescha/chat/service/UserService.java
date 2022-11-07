package com.hescha.chat.service;

import com.hescha.chat.model.User;
import com.hescha.chat.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends AbstractService<User> implements UserDetailsService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       BCryptPasswordEncoder passwordEncoder) {
        super(repository);
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username){
        return repository.findByUsernameIgnoreCase(username);
    }

    @Override
    public void create(User entity) {
        String encode = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encode);
        super.create(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> maybeUser = repository.findByUsernameIgnoreCase(username);
        if (maybeUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        User user = maybeUser.get();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                List.of());
        return userDetails;
    }
}
