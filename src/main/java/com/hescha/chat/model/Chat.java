package com.hescha.chat.model;

import com.hescha.chat.domen.ChatAvatar;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "Chats")
public class Chat extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    private ChatAvatar chatAvatar = ChatAvatar.A1;

    @ManyToMany(mappedBy = "chats", cascade = CascadeType.DETACH)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
}
