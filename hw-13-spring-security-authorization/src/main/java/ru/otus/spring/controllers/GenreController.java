package ru.otus.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;

import java.util.List;

@Controller
@AllArgsConstructor
public class GenreController {
    private static final String URL_GENRES = "/genres";
    private final GenreService genreService;

    @GetMapping("/genres")
    public String getGenres(Model model) {
        List<Genre> genres = genreService.readAll();
        model.addAttribute("genres", genres);
        return "listGenres";
    }

    @GetMapping("/genres/{id}/edit")
    public String editGenre(@PathVariable("id") String genreId, Model model) {
        Genre genre = genreService.getById(genreId);
        model.addAttribute("genre", genre);
        return "editGenre";
    }

    @GetMapping("/genres/add")
    public String addGenre(Model model) {
        model.addAttribute("genre", new Genre());
        model.addAttribute("action", "add");
        return "editGenre";
    }

    @PostMapping("/genres/{id}/remove")
    public RedirectView deleteGenre(@PathVariable("id") String genreId) {
        genreService.deleteById(genreId);
        return new RedirectView(URL_GENRES);
    }

    @PostMapping("/genres/create")
    public RedirectView saveGenre(Genre genre) {
        genreService.create(genre.getName());
        return new RedirectView(URL_GENRES);
    }

    @PostMapping("/genres/update")
    public RedirectView updateGenre(Genre genre) {
        genreService.update(genre.getId(), genre.getName());
        return new RedirectView(URL_GENRES);
    }
}
