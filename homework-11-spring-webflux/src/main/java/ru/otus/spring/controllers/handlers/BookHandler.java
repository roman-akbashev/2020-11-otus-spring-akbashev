package ru.otus.spring.controllers.handlers;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.controllers.dto.AuthorDto;
import ru.otus.spring.controllers.dto.BookDto;
import ru.otus.spring.controllers.mapper.DtoMapper;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repositories.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@Component
public class BookHandler {
    private final BookRepository bookRepository;
    private final DtoMapper<Book, BookDto> mapper;

    public @NotNull Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(bookRepository.findAll().map(mapper::toDto), BookDto.class);
    }

    public @NotNull Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .map(mapper::toEntity)
                .flatMap(bookRepository::save)
                .map(mapper::toDto)
                .flatMap(bookDto -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(bookDto), BookDto.class));
    }

    public @NotNull Mono<ServerResponse> edit(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .map(mapper::toEntity)
                .flatMap(book -> {
                    book.setId(request.pathVariable("id"));
                    return bookRepository.save(book);
                })
                .map(mapper::toDto)
                .flatMap(authorDto -> ok().contentType(APPLICATION_JSON).body(Mono.just(authorDto), AuthorDto.class));
    }

    public @NotNull Mono<ServerResponse> delete(ServerRequest request) {
        return bookRepository.deleteById(request.pathVariable("id")).then(ok().contentType(APPLICATION_JSON).build());
    }
}
