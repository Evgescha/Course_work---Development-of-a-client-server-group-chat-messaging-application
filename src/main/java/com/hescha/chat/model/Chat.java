package com.hescha.chat.model;

import com.hescha.chat.domen.ChatAvatar;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "Chats")
public class Chat extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    private ChatAvatar avatar = ChatAvatar.A1;

    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class)
    private User owner;

    @ManyToMany(mappedBy = "chats", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    @Override
    public String toString() {
        return "Chat{" +
                "name='" + name + '\'' +
                ", avatar=" + avatar +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Chat chat = (Chat) o;
        return Objects.equals(name, chat.name)
                && avatar == chat.avatar
                && Objects.equals(users, chat.users)
                && Objects.equals(messages, chat.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, avatar, users, messages);
    }
}
