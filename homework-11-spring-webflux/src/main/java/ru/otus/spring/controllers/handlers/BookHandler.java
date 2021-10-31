package ru.otus.spring.controllers.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.controllers.dto.AuthorDto;
import ru.otus.spring.controllers.dto.BookDto;
import ru.otus.spring.controllers.mapper.DtoMapper;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@Component
public class BookHandler {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final DtoMapper<Book, BookDto> mapper;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(bookRepository.findAll().map(mapper::toDto), BookDto.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .flatMap(dto -> {
                    final Mono<Author> authorMono = authorRepository.findById(dto.getAuthorId()).switchIfEmpty(Mono.error(() -> {
                        throw new EntityNotFoundException(String.format("Author with id %s not found!", dto.getAuthorId()));
                    }));

                    final Mono<Genre> genreMono = genreRepository.findById(dto.getGenreId()).switchIfEmpty(Mono.error(() -> {
                        throw new EntityNotFoundException(String.format("Genre with id %s not found!", dto.getGenreId()));
                    }));
                    return Mono.zip(authorMono, genreMono).map(tuple -> {
                        Author author = tuple.getT1();
                        Genre genre = tuple.getT2();
                        return Book.builder().name(dto.getName()).author(author).genre(genre).build();
                    })
                            .flatMap(bookRepository::save)
                            .map(mapper::toDto)
                            .flatMap(bookDto -> ok().contentType(APPLICATION_JSON).body(Mono.just(bookDto), BookDto.class));
                });
    }

    public Mono<ServerResponse> edit(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .map(mapper::toEntity)
                .flatMap(book -> {
                    book.setId(request.pathVariable("id"));
                    return bookRepository.save(book);
                })
                .map(mapper::toDto)
                .flatMap(authorDto -> ok().contentType(APPLICATION_JSON).body(Mono.just(authorDto), AuthorDto.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return bookRepository.deleteById(request.pathVariable("id")).then(ok().contentType(TEXT_PLAIN).build());
    }
}
