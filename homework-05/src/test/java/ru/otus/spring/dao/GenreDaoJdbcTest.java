package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    void insert() {
        Genre expectedGenre = Genre.builder().id(5L).name("Genre4").build();
        genreDao.insert(expectedGenre);
        Genre actualGenre = genreDao.getById(expectedGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void update() {
        Genre expectedGenre = Genre.builder().id(1L).name("Genre5").build();
        genreDao.update(expectedGenre);
        Genre actualGenre = genreDao.getById(expectedGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void getById() {
        Genre expectedGenre = Genre.builder().id(2L).name("Genre2").build();
        Genre actualGenre = genreDao.getById(expectedGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void getByName() {
        Genre genre1 = Genre.builder().id(3L).name("Genre3").build();
        Genre genre2 = Genre.builder().id(4L).name("Genre3").build();
        List<Genre> expectedGenres = List.of(genre1, genre2);
        List<Genre> actualGenresByName = genreDao.getByName(genre1.getName());
        assertThat(actualGenresByName).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @Test
    void checkRelatedBooksById() {
        assertFalse(genreDao.checkRelatedBooksById(4L));
        assertTrue(genreDao.checkRelatedBooksById(1L));
    }

    @Test
    void getAll() {
        Genre genre1 = Genre.builder().id(1L).name("Genre1").build();
        Genre genre2 = Genre.builder().id(2L).name("Genre2").build();
        Genre genre3 = Genre.builder().id(3L).name("Genre3").build();
        Genre genre4 = Genre.builder().id(4L).name("Genre3").build();
        List<Genre> expectedGenres = List.of(genre1, genre2, genre3, genre4);
        List<Genre> actualGenres = genreDao.getAll();
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @Test
    void deleteById() {
        final long genreForDeleteId = 4L;
        genreDao.deleteById(genreForDeleteId);
        assertFalse(genreDao.getById(4L).isPresent());
    }

    @Test
    void count() {
        long actualGenresCount = genreDao.count();
        Genre genre = Genre.builder().id(5L).name("Genre6").build();
        genreDao.insert(genre);
        long updatedGenresCount = genreDao.count();
        assertThat(actualGenresCount + 1L).isEqualTo(updatedGenresCount);
    }
}