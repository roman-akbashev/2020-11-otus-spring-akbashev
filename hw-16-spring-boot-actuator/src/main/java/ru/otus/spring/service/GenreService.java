package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre create(String name);

    Genre update(String id, String name);

    List<Genre> getAll();

    Genre getByName(String name);

    void deleteById(String id);

    Genre getById(String id);

    String count();
}
