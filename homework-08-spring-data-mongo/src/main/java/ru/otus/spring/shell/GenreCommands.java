package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class GenreCommands {
    private final GenreService genreService;

    @ShellMethod(value = "CreateGenre", key = {"cg", "create-genre"})
    public void createGenre(@ShellOption String genreName) {
        genreService.create(genreName);
    }

    @ShellMethod(value = "ReadGenres", key = {"rgs", "read-genres"})
    public String readGenres() {
        return convertToTable(genreService.readAll());
    }

    @ShellMethod(value = "ReadGenreByName", key = {"rgn", "read-genre-by-name"})
    public String readGenreByName(@ShellOption String genreName) {
        return convertToTable(genreService.readByName(genreName));
    }

    @ShellMethod(value = "DeleteGenre", key = {"dg", "delete-genre"})
    public void deleteGenre(@ShellOption String genreId) {
        genreService.deleteById(genreId);
    }

    @ShellMethod(value = "CountGenres", key = {"cgs", "count-genres"})
    public String countGenres() {
        return genreService.count();
    }

    private String convertToTable(List<Genre> genres) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-35s%-25s%n";
        table.append(String.format(tableFormat, "ID", "GENRE NAME"));
        genres.forEach(genre -> table.append(String.format(tableFormat, genre.getId(), genre.getName())));
        return table.toString();
    }
}
