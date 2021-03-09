package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findByName(String name);

    @Query("select case when count(b)> 0 then true else false end from Book b join b.genre where b.genre.id = :genreId")
    boolean checkRelatedBookByGenreId(long genreId);
}
