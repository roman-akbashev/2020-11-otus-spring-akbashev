package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

public interface BookService {

    void create(String name, long authorId, long genreId);

    void update(long id, String name, long authorId, long genreId);

    String readAll();

    String read(long id);

    String readByName(String name);

    void deleteById(long id);

    Book getById(long id);

    String getAllCommentsById(long id);

    String count();
}

