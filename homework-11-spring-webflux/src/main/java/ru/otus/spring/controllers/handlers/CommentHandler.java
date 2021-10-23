package ru.otus.spring.controllers.handlers;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.controllers.dto.CommentDto;
import ru.otus.spring.controllers.mapper.DtoMapper;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@RequiredArgsConstructor
@Component
public class CommentHandler {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final DtoMapper<Comment, CommentDto> mapper;

    public @NotNull Mono<ServerResponse> getAll(ServerRequest request) {
        String bookId = request.pathVariable("bookId");
        return bookRepository
                .findById(bookId)
                .switchIfEmpty(Mono.error(() -> {
                    throw new EntityNotFoundException(String.format("Book with id %s not found!", bookId));
                }))
                .map(book -> commentRepository.findAllByBookId(book.getId()))
                .flatMap(comments -> ok().contentType(APPLICATION_JSON)
                        .body(comments.map(mapper::toDto), CommentDto.class));
    }

    public @NotNull Mono<ServerResponse> create(ServerRequest request) {
        return buildMonoComment(request, request.pathVariable("bookId"))
                .flatMap(commentRepository::save)
                .map(mapper::toDto)
                .flatMap(commentDto -> ok().contentType(APPLICATION_JSON).body(Mono.just(commentDto), CommentDto.class));
    }

    public @NotNull Mono<ServerResponse> edit(ServerRequest request) {
        return buildMonoComment(request, request.pathVariable("bookId"))
                .flatMap(comment -> {
                    comment.setId(request.pathVariable("id"));
                    return commentRepository.save(comment);
                })
                .map(mapper::toDto)
                .flatMap(commentDto -> ok().contentType(APPLICATION_JSON).body(Mono.just(commentDto), CommentDto.class));
    }

    public @NotNull Mono<ServerResponse> delete(ServerRequest request) {
        return commentRepository
                .findById(request.pathVariable("id"))
                .flatMap(p -> noContent().build(commentRepository.delete(p)))
                .switchIfEmpty(notFound().build());
    }

    @NotNull
    private Mono<Comment> buildMonoComment(ServerRequest request, String bookId) {
        return request.bodyToMono(CommentDto.class)
                .map(mapper::toEntity)
                .zipWith(bookRepository.findById(bookId).switchIfEmpty(Mono.error(() -> {
                    throw new EntityNotFoundException(String.format("Book with id %s not found!", bookId));
                })))
                .map(tuple -> {
                    final Comment comment = tuple.getT1();
                    final Book book = tuple.getT2();
                    comment.setBook(book);
                    return comment;
                });
    }

}
