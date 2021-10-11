package ru.otus.spring.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.rest.dto.GenreDto;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreRestController {
    private final GenreService genreService;

    @GetMapping("/api/genres")
    public ResponseEntity<List<GenreDto>> getGenres() {
        return new ResponseEntity<>(genreService.getAll().stream().map(GenreDto::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/api/genres/{id}")
    public ResponseEntity<GenreDto> getGenre(@PathVariable String id) {
        return new ResponseEntity<>(GenreDto.toDto(genreService.getById(id)), HttpStatus.OK);
    }

    @PutMapping("/api/genres/{id}")
    public ResponseEntity<GenreDto> editGenre(@PathVariable String id, @RequestBody GenreDto genreDto) {
        return new ResponseEntity<>(GenreDto.toDto(genreService.update(id, genreDto.getName())), HttpStatus.OK);
    }

    @DeleteMapping("/api/genres/{id}")
    public ResponseEntity<GenreDto> deleteGenre(@PathVariable String id) {
        genreService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/api/genres")
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto genreDto) {
        return new ResponseEntity<>(GenreDto.toDto(genreService.create(genreDto.getName())), HttpStatus.CREATED);
    }
}
