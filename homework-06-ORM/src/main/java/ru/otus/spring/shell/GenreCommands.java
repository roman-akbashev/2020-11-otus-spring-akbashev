package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.GenreService;

@ShellComponent
@AllArgsConstructor
public class GenreCommands {
    private final GenreService genreService;

    @ShellMethod(value = "CreateGenre", key = {"cg", "create-genre"})
    public void createGenre(@ShellOption String genreName) {
        genreService.create(genreName);
    }

    @ShellMethod(value = "UpdateGenre", key = {"ug", "update-genre"})
    public void updateGenre(@ShellOption long genreId, @ShellOption String genreName) {
        genreService.update(genreId, genreName);
    }

    @ShellMethod(value = "ReadGenres", key = {"rgs", "read-genres"})
    public String readGenres() {
        return genreService.readAll();
    }

    @ShellMethod(value = "ReadGenreByName", key = {"rgn", "read-genre-by-name"})
    public String readGenreByName(@ShellOption String genreName) {
        return genreService.readByName(genreName);
    }

    @ShellMethod(value = "DeleteGenre", key = {"dg", "delete-genre"})
    public void deleteGenre(@ShellOption long genreId) {
        genreService.deleteById(genreId);
    }

    @ShellMethod(value = "CountGenres", key = {"cgs", "count-genres"})
    public String countGenres() {
        return genreService.count();
    }
}
