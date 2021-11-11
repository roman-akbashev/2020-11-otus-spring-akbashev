package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    List<Book> findByName(String name);

    Optional<Book> findByNameAndAuthor_IdAndGenre_Id(String name, String authorId, String genreId);
}
