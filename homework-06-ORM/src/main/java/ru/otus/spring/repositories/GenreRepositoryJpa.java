package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() != null) {
            return em.merge(genre);
        } else {
            em.persist(genre);
            return genre;
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public void remove(Genre genre) {
        em.remove(genre);
    }

    @Override
    public boolean checkRelatedBooksByGenre(Genre genre) {
        TypedQuery<Long> query = em.createQuery("select count(b) from Book b join b.genre where b.genre.id = :genreId", Long.class);
        query.setParameter("genreId", genre.getId());
        long booksCountForGenre = query.getSingleResult();
        return booksCountForGenre > 0;
    }

    @Override
    public long count() {
        return em.createQuery("select count(g) from Genre g", Long.class).getSingleResult();
    }
}
