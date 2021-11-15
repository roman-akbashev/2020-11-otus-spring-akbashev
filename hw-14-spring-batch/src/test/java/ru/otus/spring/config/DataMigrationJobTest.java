package ru.otus.spring.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.model.entities.AuthorJpa;
import ru.otus.spring.model.entities.BookJpa;
import ru.otus.spring.model.entities.GenreJpa;
import ru.otus.spring.repository.jpa.AuthorRepositoryJpa;
import ru.otus.spring.repository.jpa.BookRepositoryJpa;
import ru.otus.spring.repository.jpa.GenreRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBatchTest
class DataMigrationJobTest {

    private final JobLauncherTestUtils jobLauncherTestUtils;
    private final JobRepositoryTestUtils jobRepositoryTestUtils;
    private final BookRepositoryJpa bookRepositoryJpa;
    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    public DataMigrationJobTest(JobLauncherTestUtils jobLauncherTestUtils, JobRepositoryTestUtils jobRepositoryTestUtils,
                                BookRepositoryJpa bookRepositoryJpa, AuthorRepositoryJpa authorRepositoryJpa,
                                GenreRepositoryJpa genreRepositoryJpa) {
        this.jobLauncherTestUtils = jobLauncherTestUtils;
        this.jobRepositoryTestUtils = jobRepositoryTestUtils;
        this.bookRepositoryJpa = bookRepositoryJpa;
        this.authorRepositoryJpa = authorRepositoryJpa;
        this.genreRepositoryJpa = genreRepositoryJpa;
    }

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo("dataMigrationJob");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        List<AuthorJpa> authors = authorRepositoryJpa.findAll();

        assertThat(authors).hasSize(3);
        assertAll(
                () -> assertEquals("Swift", authors.get(0).getName()),
                () -> assertEquals(1L, authors.get(0).getId())
        );
        assertAll(
                () -> assertEquals("Dostoevsky", authors.get(1).getName()),
                () -> assertEquals(2L, authors.get(1).getId())
        );
        assertAll(
                () -> assertEquals("Goethe", authors.get(2).getName()),
                () -> assertEquals(3L, authors.get(2).getId())
        );

        List<GenreJpa> genres = genreRepositoryJpa.findAll();
        assertThat(genres).hasSize(3);
        assertAll(
                () -> assertEquals("Satire", genres.get(0).getName()),
                () -> assertEquals(1L, genres.get(0).getId())
        );
        assertAll(
                () -> assertEquals("Novel", genres.get(1).getName()),
                () -> assertEquals(2L, genres.get(1).getId())
        );
        assertAll(
                () -> assertEquals("Tragedy", genres.get(2).getName()),
                () -> assertEquals(3L, genres.get(2).getId())
        );

        List<BookJpa> books = bookRepositoryJpa.findAll();

        assertThat(books).hasSize(1);
        BookJpa book = books.get(0);
        assertAll(
                () -> assertEquals(1L, book.getId()),
                () -> assertEquals("Gulliver", book.getName()),
                () -> assertEquals(1L, book.getAuthorJpa().getId()),
                () -> assertEquals("Swift", book.getAuthorJpa().getName()),
                () -> assertEquals(1L, book.getGenreJpa().getId()),
                () -> assertEquals("Satire", book.getGenreJpa().getName())
        );
    }
}