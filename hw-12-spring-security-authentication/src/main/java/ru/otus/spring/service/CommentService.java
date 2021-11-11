package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentService {

    void create(String commentText, String bookId);

    void update(String id, String commentText);

    void deleteById(String id);

    Comment getById(String id);

    List<Comment> readAllByBookId(String bookId);

    void deleteAllByBookId(String bookId);
}
