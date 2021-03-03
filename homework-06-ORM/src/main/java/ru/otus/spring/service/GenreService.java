package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

public interface GenreService {

    void create(String name);

    void update(long id, String name);

    String readAll();

    String readByName(String name);

    void deleteById(long id);

    Genre getById(long id);

    String count();
}
