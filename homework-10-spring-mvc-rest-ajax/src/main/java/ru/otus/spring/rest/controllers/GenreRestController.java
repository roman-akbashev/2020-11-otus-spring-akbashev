package ru.otus.spring.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.rest.dto.GenreDto;
import ru.otus.spring.service.GenreService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreRestController {
    private final GenreService genreService;

    @GetMapping("/api/genres")
    public List<GenreDto> getGenres() {
        return genreService.getAll().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/genres/{id}")
    public GenreDto getGenre(@PathVariable String id) {
        return GenreDto.toDto(genreService.getById(id));
    }

    @PutMapping("/api/genres/{id}")
    public Map<String, Boolean> editGenre(@PathVariable String id, @RequestBody GenreDto genreDto) {
        genreService.update(id, genreDto.getName());
        return Collections.singletonMap("edited", Boolean.TRUE);
    }

    @DeleteMapping("/api/genres/{id}")
    public Map<String, Boolean> deleteGenre(@PathVariable String id) {
        genreService.deleteById(id);
        return Collections.singletonMap("deleted", Boolean.TRUE);
    }

    @PostMapping("/api/genres")
    public Map<String, Boolean> createGenre(@RequestBody GenreDto genreDto) {
        genreService.create(genreDto.getName());
        return Collections.singletonMap("created", Boolean.TRUE);
    }
}
