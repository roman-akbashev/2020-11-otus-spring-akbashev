package ru.otus.spring.repositories;

import ru.otus.spring.domain.Comment;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    void remove(Comment comment);

}
