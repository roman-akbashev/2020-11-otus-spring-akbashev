package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

@JdbcTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void insert() {
        Genre expectedGenre = Genre.builder().id(5L).name("Genre4").build();
        genreRepository.save(expectedGenre);
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void update() {
        Genre expectedGenre = Genre.builder().id(1L).name("Genre5").build();
        genreRepository.save(expectedGenre);
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void getById() {
        Genre expectedGenre = Genre.builder().id(2L).name("Genre2").build();
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void getByName() {
        Genre genre1 = Genre.builder().id(3L).name("Genre3").build();
        Genre genre2 = Genre.builder().id(4L).name("Genre3").build();
        List<Genre> expectedGenres = List.of(genre1, genre2);
        List<Genre> actualGenresByName = genreRepository.findByName(genre1.getName());
        assertThat(actualGenresByName).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @Test
    void getAll() {
        Genre genre1 = Genre.builder().id(1L).name("Genre1").build();
        Genre genre2 = Genre.builder().id(2L).name("Genre2").build();
        Genre genre3 = Genre.builder().id(3L).name("Genre3").build();
        Genre genre4 = Genre.builder().id(4L).name("Genre3").build();
        List<Genre> expectedGenres = List.of(genre1, genre2, genre3, genre4);
        List<Genre> actualGenres = genreRepository.findAll();
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @Test
    void deleteById() {
        final long genreForDeleteId = 4L;
        genreRepository.remove(Genre.builder().id(genreForDeleteId).build());
        assertFalse(genreRepository.findById(4L).isPresent());
    }

    @Test
    void count() {
        long actualGenresCount = genreRepository.count();
        Genre genre = Genre.builder().id(5L).name("Genre6").build();
        genreRepository.save(genre);
        long updatedGenresCount = genreRepository.count();
        assertThat(actualGenresCount + 1L).isEqualTo(updatedGenresCount);
    }
}