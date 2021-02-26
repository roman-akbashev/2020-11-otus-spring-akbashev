package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findByName(String name) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.name = :name", Book.class);
        query.setParameter("name", name);
        EntityGraph<?> entityGraph = em.getEntityGraph("author-and-genre-entity-graph");
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        EntityGraph<?> entityGraph = em.getEntityGraph("author-and-genre-entity-graph");
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void remove(Book book) {
        em.remove(book);
    }

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }
}
