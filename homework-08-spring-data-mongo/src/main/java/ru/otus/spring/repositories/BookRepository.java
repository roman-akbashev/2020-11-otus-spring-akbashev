package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByName(String name);

    @ExistsQuery(value = "{ 'author._id' : ?0 }")
    boolean checkExistsByAuthorId(String authorId);

    @ExistsQuery(value = "{ 'genre._id' : ?0 }")
    boolean checkExistsByGenreId(String genreId);
}
