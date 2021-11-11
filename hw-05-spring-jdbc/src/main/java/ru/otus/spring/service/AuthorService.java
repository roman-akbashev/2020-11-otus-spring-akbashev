package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

public interface AuthorService {

    void create(String name);

    void update(long id, String name);

    String readAll();

    String readByName(String name);

    void deleteById(long id);

    Author getById(long id);

    String count();
}
