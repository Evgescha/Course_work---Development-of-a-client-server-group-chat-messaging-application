package com.hescha.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Messages")
public class Message extends AbstractEntity {
    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class)
    private User owner;

    @ManyToOne
    @JsonIgnore
    private Chat chat;

    @Column(nullable = false)
    private String text;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Override
    public String toString() {
        return "Message{" +
                "owner=" + owner.getUsername() +
                ", chat=" + chat.getName() +
                ", text='" + text + '\'' +
                '}';
    }
}
