package ru.otus.spring.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.rest.dto.BookDto;
import ru.otus.spring.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> getBooks() {
        return bookService.getAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable String id) {
        return BookDto.toDto(bookService.getById(id));
    }

    @PutMapping("/api/books/{id}")
    public Map<String, Boolean> editBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        bookService.update(id, bookDto.getName(), bookDto.getAuthor().getId(), bookDto.getGenre().getId());
        return Collections.singletonMap("edited", Boolean.TRUE);
    }

    @DeleteMapping("/api/books/{id}")
    public Map<String, Boolean> deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
        return Collections.singletonMap("deleted", Boolean.TRUE);
    }

    @PostMapping("/api/books")
    public Map<String, Boolean> createBook(@RequestBody BookDto bookDto) {
        bookService.create(bookDto.getName(), bookDto.getAuthor().getId(), bookDto.getGenre().getId());
        return Collections.singletonMap("created", Boolean.TRUE);
    }
}
