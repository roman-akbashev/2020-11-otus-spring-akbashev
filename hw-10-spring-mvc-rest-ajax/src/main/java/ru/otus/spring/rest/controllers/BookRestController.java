package ru.otus.spring.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.rest.dto.BookDto;
import ru.otus.spring.rest.mapper.DtoMapper;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;
    private final DtoMapper<Book, BookDto> mapper;

    @GetMapping("/api/books")
    public ResponseEntity<List<BookDto>> getBooks() {
        return new ResponseEntity<>(bookService.getAll().stream().map(mapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable String id) {
        return new ResponseEntity<>(mapper.toDto(bookService.getById(id)), HttpStatus.OK);
    }

    @PutMapping("/api/books/{id}")
    public ResponseEntity<BookDto> editBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(mapper.toDto(bookService.update(id, bookDto.getName(), bookDto.getAuthor().getId(),
                bookDto.getGenre().getId())), HttpStatus.OK);
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/api/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(mapper.toDto(bookService.create(bookDto.getName(), bookDto.getAuthor().getId(),
                bookDto.getGenre().getId())), HttpStatus.CREATED);
    }
}
