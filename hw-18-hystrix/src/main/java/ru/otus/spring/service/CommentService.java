package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment create(String commentText, String bookId);

    Comment update(String id, String commentText);

    void deleteById(String id);

    Comment getById(String id);

    List<Comment> getAllByBookId(String bookId);
}
