package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.AuthorService;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class AuthorCommands {
    private final AuthorService authorService;

    @ShellMethod(value = "CreateAuthor", key = {"ca", "create-author"})
    public void createAuthor(@ShellOption String authorName) {
        authorService.create(authorName);
    }

    @ShellMethod(value = "UpdateAuthor", key = {"ua", "update-author"})
    public void updateAuthor(@ShellOption String authorId, @ShellOption String authorName) {
        authorService.update(authorId, authorName);
    }

    @ShellMethod(value = "ReadAuthors", key = {"ras", "read-authors"})
    public String readAuthors() {
        return convertToTable(authorService.readAll());
    }

    @ShellMethod(value = "ReadAuthorByName", key = {"ran", "read-author-by-name"})
    public String readAuthorByName(@ShellOption String authorName) {
        return convertToTable(authorService.readByName(authorName));
    }

    @ShellMethod(value = "DeleteAuthor", key = {"da", "delete-author"})
    public void deleteAuthor(@ShellOption String authorId) {
        authorService.deleteById(authorId);
    }

    @ShellMethod(value = "CountAuthors", key = {"cas", "count-authors"})
    public String countAuthors() {
        return authorService.count();
    }

    private String convertToTable(List<Author> authors) {
        String tableFormat = "%-35s%-25s%n";
        StringBuffer table = new StringBuffer();
        table.append(String.format(tableFormat, "ID", "AUTHOR NAME"));
        authors.forEach(author -> table.append(String.format(tableFormat, author.getId(), author.getName())));
        return table.toString();
    }

}
