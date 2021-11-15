package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.model.entities.AuthorJpa;
import ru.otus.spring.model.entities.BookJpa;
import ru.otus.spring.model.entities.GenreJpa;
import ru.otus.spring.repository.jpa.AuthorRepositoryJpa;
import ru.otus.spring.repository.jpa.BookRepositoryJpa;
import ru.otus.spring.repository.jpa.GenreRepositoryJpa;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {
    private final Job job;
    private final JobLauncher jobLauncher;
    private final BookRepositoryJpa bookRepositoryJpa;
    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;

    @ShellMethod(value = "startMigrationJobMongoToJpa", key = {"smj", "startMigrationJob"})
    public void startMigrationJobMongoToJpa() throws Exception {
        jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
    }

    @ShellMethod(value = "read jpa books", key = {"rbs", "readBooks"})
    public String readJpaBooks() {
        return convertBookListToTable(bookRepositoryJpa.findAll());
    }

    @ShellMethod(value = "read jpa authors", key = {"ras", "readAuthors"})
    public String readJpaAuthors() {
        return convertAuthorListToTable(authorRepositoryJpa.findAll());
    }

    @ShellMethod(value = "read jpa genres", key = {"rgs", "readGenres"})
    public String readJpaGenres() {
        return convertGenreListToTable(genreRepositoryJpa.findAll());
    }

    private String convertBookListToTable(List<BookJpa> books) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-10s%-20s%-20s%-20s%n";
        table.append(String.format(tableFormat, "ID", "BOOK NAME", "BOOK AUTHOR", "BOOK GENRE"));
        books.forEach(book -> table.append(String.format(tableFormat, book.getId(), book.getName(),
                book.getAuthorJpa().getName(), book.getGenreJpa().getName())));
        return table.toString();
    }

    private String convertAuthorListToTable(List<AuthorJpa> authors) {
        String tableFormat = "%-10s%-20s%n";
        StringBuffer table = new StringBuffer();
        table.append(String.format(tableFormat, "ID", "AUTHOR NAME"));
        authors.forEach(author -> table.append(String.format(tableFormat, author.getId(), author.getName())));
        return table.toString();
    }

    private String convertGenreListToTable(List<GenreJpa> genres) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-10s%-20s%n";
        table.append(String.format(tableFormat, "ID", "GENRE NAME"));
        genres.forEach(genre -> table.append(String.format(tableFormat, genre.getId(), genre.getName())));
        return table.toString();
    }
}