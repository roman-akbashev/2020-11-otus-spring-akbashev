package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.AbstractRepositoryTest;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

class GenreRepositoryWithoutListenerTest extends AbstractRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void insert() {
        Genre expectedGenre = genreRepository.save(Genre.builder().name("Genre3").build());
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void update() {
        Genre expectedGenre = Genre.builder().id("1").name("Genre4").build();
        genreRepository.save(expectedGenre);
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void getById() {
        Genre expectedGenre = Genre.builder().id("2").name("Genre2").build();
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void getByName() {
        Genre expectedGenre = Genre.builder().id("2").name("Genre2").build();
        Genre actualGenre = genreRepository.findByName(expectedGenre.getName()).orElseGet(() -> fail("Genre not found!"));
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void getAll() {
        Genre genre1 = Genre.builder().id("1").name("Genre1").build();
        Genre genre2 = Genre.builder().id("2").name("Genre2").build();
        List<Genre> expectedGenres = List.of(genre1, genre2);
        List<Genre> actualGenres = genreRepository.findAll();
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @Test
    void remove() {
        Genre newGenre = genreRepository.save(Genre.builder().name("new Genre").build());
        Genre genre = genreRepository.findById(newGenre.getId()).orElseGet(() -> fail("Genre not found!"));
        genreRepository.deleteById(genre.getId());
        assertFalse(genreRepository.findById(genre.getId()).isPresent());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldNotRemoveFromBookWhenRemoved() {
        Book book = bookRepository.findById("1").orElseGet(() -> fail("Book not found"));
        Genre genre = genreRepository.findById(book.getGenre().getId()).orElseGet(() -> fail("Genre not found!"));
        genreRepository.deleteById(genre.getId());
        assertThat(bookRepository.findById("1")).isNotEmpty().get().extracting(Book::getGenre).isNotNull();
    }

    @Test
    void count() {
        long actualGenresCount = genreRepository.count();
        Genre genre = Genre.builder().name("Genre5").build();
        genreRepository.save(genre);
        long updatedGenresCount = genreRepository.count();
        assertThat(actualGenresCount + 1L).isEqualTo(updatedGenresCount);
    }
}