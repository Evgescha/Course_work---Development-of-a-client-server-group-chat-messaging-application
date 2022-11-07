package com.hescha.chat.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Messages")
public class Message extends AbstractEntity {
    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class)
    private User owner;

    @ManyToOne
    private Chat chat;

    @Column(nullable = false)
    private String text;
}
