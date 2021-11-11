package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findByName(String name);

    @EntityGraph(attributePaths = {"author", "genre"})
    @Override
    List<Book> findAll();
}
