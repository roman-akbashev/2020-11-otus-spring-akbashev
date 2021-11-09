package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorService {

    void create(String name);

    void update(String id, String name);

    List<Author> readAll();

    List<Author> readByName(String name);

    void deleteById(String id);

    Author getById(String id);

    String count();
}
