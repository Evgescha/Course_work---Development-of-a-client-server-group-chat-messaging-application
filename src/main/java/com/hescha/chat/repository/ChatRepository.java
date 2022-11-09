package com.hescha.chat.repository;

import com.hescha.chat.model.Chat;
import com.hescha.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByNameIgnoreCase(String name);

    List<Chat> findByOwner(User owner);

    List<Chat> findByUsersContains(User user);
}
