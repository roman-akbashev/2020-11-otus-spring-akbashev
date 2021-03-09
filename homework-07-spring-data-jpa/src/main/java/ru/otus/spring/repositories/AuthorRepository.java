package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByName(String name);

    @Query("select case when count(b)> 0 then true else false end from Book b join b.author where b.author.id = :authorId")
    boolean checkRelatedBookByAuthorId(long authorId);
}
