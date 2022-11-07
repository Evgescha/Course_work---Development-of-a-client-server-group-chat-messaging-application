package com.hescha.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractService<E> {
    private final JpaRepository<E, Long> repository;

    void create(E entity) {
        repository.saveAndFlush(entity);
    }

    List<E> read() {
        return repository.findAll();
    }

    Optional<E> findById(int id) {
        return repository.findById((long) id);
    }

    void update(E entity) {
        repository.saveAndFlush(entity);
    }

    void delete(E entity) {
        repository.delete(entity);
    }

    void delete(int id) {
        repository.deleteById((long) id);
    }
}
