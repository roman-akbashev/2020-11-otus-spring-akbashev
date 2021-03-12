package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorService {

    void create(String name);

    void update(long id, String name);

    List<Author> readAll();

    List<Author> readByName(String name);

    void deleteById(long id);

    Author getById(long id);

    String count();
}
