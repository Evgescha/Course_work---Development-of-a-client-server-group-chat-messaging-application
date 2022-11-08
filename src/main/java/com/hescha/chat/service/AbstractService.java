package com.hescha.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractService<E> {
    private final JpaRepository<E, Long> repository;

    public E create(E entity) {
        return repository.saveAndFlush(entity);
    }

    public List<E> read() {
        return repository.findAll();
    }

    public Optional<E> findById(int id) {
        return repository.findById((long) id);
    }

    public E update(E entity) {
        return repository.saveAndFlush(entity);
    }

    public void delete(E entity) {
        repository.delete(entity);
    }

    public void delete(int id) {
        repository.deleteById((long) id);
    }
}
