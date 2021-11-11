package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    void insert(Book book);

    void update(Book book);

    Optional<Book> getById(long id);

    List<Book> getByName(String name);

    List<Book> getAll();

    void deleteById(long id);

    long count();
}
