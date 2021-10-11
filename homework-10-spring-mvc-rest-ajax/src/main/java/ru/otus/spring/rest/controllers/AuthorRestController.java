package ru.otus.spring.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.rest.dto.AuthorDto;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {
    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        return new ResponseEntity<>(authorService.getAll().stream().map(AuthorDto::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/api/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable String id) {
        return new ResponseEntity<>(AuthorDto.toDto(authorService.getById(id)), HttpStatus.OK);
    }

    @PutMapping("/api/authors/{id}")
    public ResponseEntity<AuthorDto> editAuthor(@PathVariable String id, @RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(AuthorDto.toDto(authorService.update(id, authorDto.getName())), HttpStatus.OK);
    }

    @DeleteMapping("/api/authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable String id) {
        authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/api/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(AuthorDto.toDto(authorService.create(authorDto.getName())), HttpStatus.CREATED);
    }
}
