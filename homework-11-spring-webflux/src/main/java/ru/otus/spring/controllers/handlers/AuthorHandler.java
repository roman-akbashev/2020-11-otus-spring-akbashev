package ru.otus.spring.controllers.handlers;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.controllers.dto.AuthorDto;
import ru.otus.spring.controllers.mapper.DtoMapper;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@Component
public class AuthorHandler {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final DtoMapper<Author, AuthorDto> mapper;

    public @NotNull Mono<ServerResponse> getAll(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(authorRepository.findAll().map(mapper::toDto), AuthorDto.class);
    }

    public @NotNull Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(AuthorDto.class)
                .map(mapper::toEntity)
                .flatMap(authorRepository::save)
                .map(mapper::toDto)
                .flatMap(authorDto -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(authorDto), AuthorDto.class));
    }

    public @NotNull Mono<ServerResponse> edit(ServerRequest request) {
        return request.bodyToMono(AuthorDto.class)
                .map(mapper::toEntity)
                .flatMap(author -> {
                    author.setId(request.pathVariable("id"));
                    return authorRepository.save(author);
                })
                .map(mapper::toDto)
                .flatMap(authorDto -> ok().contentType(APPLICATION_JSON).body(Mono.just(authorDto), AuthorDto.class));
    }

    public @NotNull Mono<ServerResponse> delete(ServerRequest request) {
        final String authorId = request.pathVariable("id");
        return authorRepository.findById(authorId)
                .flatMap(author ->
                        bookRepository.existsByAuthorId(authorId)
                                .flatMap(authorHasBook -> {
                                    if (authorHasBook) {
                                        return Mono.error(new EntityCanNotBeDeletedException("Author with id " + authorId +
                                                " can not be deleted because there is a link with the book"));
                                    }
                                    return authorRepository.deleteById(authorId);
                                })
                )
                .then(ok().contentType(APPLICATION_JSON).build())
                .switchIfEmpty(notFound().build());
    }
}
