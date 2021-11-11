package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Boolean> existsByAuthorId(String authorId);

    Mono<Boolean> existsByGenreId(String genreId);

}
