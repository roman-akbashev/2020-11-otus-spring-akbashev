package ru.otus.spring.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.rest.dto.AuthorDto;
import ru.otus.spring.service.AuthorService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {
    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public List<AuthorDto> getAuthors() {
        return authorService.getAll().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/authors/{id}")
    public AuthorDto getAuthor(@PathVariable String id) {
        return AuthorDto.toDto(authorService.getById(id));
    }

    @PutMapping("/api/authors/{id}")
    public Map<String, Boolean> editAuthor(@PathVariable String id, @RequestBody AuthorDto authorDto) {
        authorService.update(id, authorDto.getName());
        return Collections.singletonMap("edited", Boolean.TRUE);
    }

    @DeleteMapping("/api/authors/{id}")
    public Map<String, Boolean> deleteAuthor(@PathVariable String id) {
        authorService.deleteById(id);
        return Collections.singletonMap("deleted", Boolean.TRUE);
    }

    @PostMapping("/api/authors")
    public Map<String, Boolean> createAuthor(@RequestBody AuthorDto authorDto) {
        authorService.create(authorDto.getName());
        return Collections.singletonMap("created", Boolean.TRUE);
    }
}
