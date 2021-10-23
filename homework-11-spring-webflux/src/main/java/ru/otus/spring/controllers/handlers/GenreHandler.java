package ru.otus.spring.controllers.handlers;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;
import ru.otus.spring.controllers.dto.GenreDto;
import ru.otus.spring.controllers.mapper.DtoMapper;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@Component
public class GenreHandler {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final DtoMapper<Genre, GenreDto> mapper;

    public @NotNull Mono<ServerResponse> getAll(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(genreRepository.findAll().map(mapper::toDto), GenreDto.class);
    }

    public @NotNull Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(GenreDto.class)
                .map(mapper::toEntity)
                .flatMap(genreRepository::save)
                .map(mapper::toDto)
                .flatMap(genreDto -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(genreDto), GenreDto.class));
    }

    public @NotNull Mono<ServerResponse> edit(ServerRequest request) {
        return request.bodyToMono(GenreDto.class)
                .map(mapper::toEntity)
                .flatMap(genre -> {
                    genre.setId(request.pathVariable("id"));
                    return genreRepository.save(genre);
                })
                .map(mapper::toDto)
                .flatMap(genreDto -> ok().contentType(APPLICATION_JSON).body(Mono.just(genreDto), GenreDto.class));
    }

    public @NotNull Mono<ServerResponse> delete(ServerRequest request) {
        final String genreId = request.pathVariable("id");
        return genreRepository.findById(genreId)
                .flatMap(genre ->
                        bookRepository.existsByGenreId(genreId)
                                .flatMap(genreHasBook -> {
                                    if (genreHasBook) {
                                        return Mono.error(new EntityCanNotBeDeletedException("Genre with id " + genreId +
                                                " can not be deleted because there is a link with the book"));
                                    }
                                    return genreRepository.deleteById(genreId);
                                })
                )
                .then(ok().contentType(APPLICATION_JSON).build())
                .switchIfEmpty(notFound().build());
    }
}