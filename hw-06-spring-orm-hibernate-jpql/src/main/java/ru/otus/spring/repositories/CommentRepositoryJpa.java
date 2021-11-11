package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() != null) {
            return em.merge(comment);
        } else {
            em.persist(comment);
            return comment;
        }
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public void remove(Comment comment) {
        em.remove(comment);
    }
}
