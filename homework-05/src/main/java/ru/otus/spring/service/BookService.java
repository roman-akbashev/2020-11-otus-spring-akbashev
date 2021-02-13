package ru.otus.spring.service;

public interface BookService {

    void create(String name, long authorId, long genreId);

    void update(long id, String name, long authorId, long genreId);

    String readAll();

    String read(long id);

    String readByName(String name);

    void deleteById(long id);

    String count();
}

