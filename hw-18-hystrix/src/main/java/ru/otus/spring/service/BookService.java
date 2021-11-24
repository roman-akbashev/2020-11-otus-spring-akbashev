package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {

    Book create(String name, String authorId, String genreId);

    Book update(String id, String name, String authorId, String genreId);

    List<Book> getAll();

    void deleteById(String id);

    Book getById(String id);

}

