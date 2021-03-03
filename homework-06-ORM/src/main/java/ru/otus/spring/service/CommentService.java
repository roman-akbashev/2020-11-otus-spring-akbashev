package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

public interface CommentService {

    void create(String commentText, long bookId);

    void update(long id, String commentText);

    void deleteById(long id);

    Comment getById(long id);

}
