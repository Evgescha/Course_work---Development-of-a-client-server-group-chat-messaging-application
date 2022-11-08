package com.hescha.chat.repository;

import com.hescha.chat.model.Chat;
import com.hescha.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatAndIdGreaterThan(Chat chat, Long id);
}
