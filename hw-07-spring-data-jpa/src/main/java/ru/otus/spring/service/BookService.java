package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface BookService {

    void create(String name, long authorId, long genreId);

    void update(long id, String name, long authorId, long genreId);

    List<Book> readAll();

    String read(long id);

    List<Book> readByName(String name);

    void deleteById(long id);

    Book getById(long id);

    List<Comment> getAllCommentsById(long id);

    String count();
}

