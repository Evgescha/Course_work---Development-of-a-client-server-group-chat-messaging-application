package com.hescha.chat.model;

import com.hescha.chat.domen.UserAvatar;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "Users")
public class User extends AbstractEntity {
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    private UserAvatar userAvatar = UserAvatar.A1;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<Chat> chats = new HashSet<>();
}
