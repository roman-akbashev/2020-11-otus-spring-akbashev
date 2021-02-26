package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author save(Author author) {
        if (author.getId() != null) {
            return em.merge(author);
        } else {
            em.persist(author);
            return author;
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void remove(Author author) {
        em.remove(author);
    }

    @Override
    public boolean checkRelatedBooksByAuthor(Author author) {
        TypedQuery<Long> query = em.createQuery("select count(b) from Book b join b.author where b.author.id = :authorId", Long.class);
        query.setParameter("authorId", author.getId());
        long booksCountForAuthor = query.getSingleResult();
        return booksCountForAuthor > 0;
    }

    @Override
    public long count() {
        return em.createQuery("select count(a) from Author a", Long.class).getSingleResult();
    }
}
