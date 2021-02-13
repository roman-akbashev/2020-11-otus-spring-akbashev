package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    void insert(Genre genre);

    void update(Genre genre);

    Optional<Genre> getById(long id);

    List<Genre> getByName(String name);

    boolean checkRelatedBooksById(long id);

    List<Genre> getAll();

    void deleteById(long id);

    long count();
}
