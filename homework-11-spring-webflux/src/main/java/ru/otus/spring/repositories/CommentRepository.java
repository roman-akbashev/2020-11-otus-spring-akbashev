package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findAllByBookId(String bookId);

    Mono<Void> deleteAllByBookId(String bookId);
}
