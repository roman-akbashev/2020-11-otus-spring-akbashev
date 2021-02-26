package ru.otus.spring.repositories;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);

    Optional<Author> findById(long id);

    List<Author> findByName(String name);

    List<Author> findAll();

    void remove(Author author);

    boolean checkRelatedBooksByAuthor(Author author);

    long count();
}
