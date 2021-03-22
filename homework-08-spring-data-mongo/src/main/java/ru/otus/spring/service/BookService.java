package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {

    void create(String name, String authorId, String genreId);

    void update(String id, String name, String authorId, String genreId);

    List<Book> readAll();

    String read(String id);

    List<Book> readByName(String name);

    void deleteById(String id);

    Book getById(String id);

    String count();
}

