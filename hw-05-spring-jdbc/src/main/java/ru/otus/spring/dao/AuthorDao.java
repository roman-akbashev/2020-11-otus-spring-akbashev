package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    void insert(Author author);

    void update(Author author);

    Optional<Author> getById(long id);

    List<Author> getByName(String name);

    boolean checkRelatedBooksById(long id);

    List<Author> getAll();

    void deleteById(long id);

    long count();
}
