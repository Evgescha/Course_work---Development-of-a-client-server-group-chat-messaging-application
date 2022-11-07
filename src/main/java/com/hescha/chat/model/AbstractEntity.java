package com.hescha.chat.model;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
}
