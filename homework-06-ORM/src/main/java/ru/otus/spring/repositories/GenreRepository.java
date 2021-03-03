package ru.otus.spring.repositories;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findByName(String name);

    List<Genre> findAll();

    void remove(Genre author);

    boolean checkRelatedBookByGenre(Genre genre);

    long count();
}
