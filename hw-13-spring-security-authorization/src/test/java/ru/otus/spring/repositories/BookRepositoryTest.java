package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.AbstractRepositoryTest;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest extends AbstractRepositoryTest {

    private static final long EXPECTED_BOOK_COUNT = 2L;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void insert() {
        Author author = Author.builder().id("3").name("Author3").build();
        Genre genre = Genre.builder().id("3").name("Genre3").build();
        Book expectedBook = Book.builder().id("3").name("Book3").author(author).genre(genre).build();
        bookRepository.save(expectedBook);
        Book actualBook = bookRepository.findById(expectedBook.getId()).orElseGet(() -> fail("Book not found"));
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void update() {
        Author author = Author.builder().id("3").name("Author3").build();
        Genre genre = Genre.builder().id("3").name("Genre3").build();
        Book expectedBook = Book.builder().id("1").name("BookModified").author(author).genre(genre).build();
        bookRepository.save(expectedBook);
        Book actualBook = bookRepository.findById(expectedBook.getId()).orElseGet(() -> fail("Book not found"));
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void getById() {
        Author author = Author.builder().id("2").name("Author2").build();
        Genre genre = Genre.builder().id("2").name("Genre2").build();
        Book expectedBook = Book.builder().id("2").name("Book2").author(author).genre(genre).build();
        Book actualBook = bookRepository.findById(expectedBook.getId()).orElseGet(() -> fail("Book not found"));
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void getByName() {
        Author author = Author.builder().id("1").name("Author1").build();
        Genre genre = Genre.builder().id("1").name("Genre1").build();
        Book book3 = Book.builder().id("3").name("Book3").author(author).genre(genre).build();
        Book book4 = Book.builder().id("4").name("Book3").author(author).genre(genre).build();
        bookRepository.save(book3);
        bookRepository.save(book4);
        List<Book> expectedBooks = List.of(book3, book4);
        List<Book> actualBooksByName = bookRepository.findByName(book3.getName());
        assertThat(actualBooksByName).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void checkExistsByAuthor() {
        Author author1 = Author.builder().id("1").name("Author1").build();
        Author author3 = Author.builder().id("3").name("Author3").build();
        assertTrue(bookRepository.checkExistsByAuthorId(author1.getId()));
        assertFalse(bookRepository.checkExistsByAuthorId(author3.getId()));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void checkExistsByGenre() {
        Genre genre1 = Genre.builder().id("1").name("Genre1").build();
        Genre genre3 = Genre.builder().id("3").name("Genre3").build();
        assertTrue(bookRepository.checkExistsByGenreId(genre1.getId()));
        assertFalse(bookRepository.checkExistsByGenreId(genre3.getId()));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void getAll() {
        Author author1 = Author.builder().id("1").name("Author1").build();
        Genre genre1 = Genre.builder().id("1").name("Genre1").build();
        Author author2 = Author.builder().id("2").name("Author2").build();
        Genre genre2 = Genre.builder().id("2").name("Genre2").build();
        Book book1 = Book.builder().id("1").name("Book1").author(author1).genre(genre1).build();
        Book book2 = Book.builder().id("2").name("Book2").author(author2).genre(genre2).build();
        List<Book> expectedBooks = List.of(book1, book2);
        List<Book> actualBooksByName = bookRepository.findAll();
        assertThat(actualBooksByName).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void deleteById() {
        final String bookForDeleteId = "1";
        bookRepository.deleteById(bookForDeleteId);
        assertFalse(bookRepository.findById(bookForDeleteId).isPresent());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void count() {
        long actualBookCount = bookRepository.count();
        assertThat(actualBookCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }
}