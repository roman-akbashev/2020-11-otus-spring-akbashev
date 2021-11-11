package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    void create(String name);

    void update(String id, String name);

    List<Genre> readAll();

    List<Genre> readByName(String name);

    void deleteById(String id);

    Genre getById(String id);

    String count();
}
