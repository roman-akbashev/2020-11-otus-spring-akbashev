package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.AbstractRepositoryTest;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

class AuthorRepositoryWithoutListenerTest extends AbstractRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void insert() {
        Author expectedAuthor = authorRepository.save(Author.builder().name("Author4").build());
        Author actualAuthor = authorRepository.findById(expectedAuthor.getId()).orElseGet(() -> fail("Author not found!"));
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void findById() {
        Author expectedAuthor = Author.builder().id("2").name("Author2").build();
        Author actualAuthor = authorRepository.findById(expectedAuthor.getId()).orElseGet(() -> fail("Author not found!"));
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void getByName() {
        Author author1 = Author.builder().id("3").name("Author3").build();
        Author author2 = Author.builder().id("4").name("Author3").build();
        List<Author> expectedAuthors = List.of(author1, author2);
        List<Author> actualAuthorsByName = authorRepository.findByName(author1.getName());
        System.out.println("actualAuthorsByName " + actualAuthorsByName);
        assertThat(actualAuthorsByName).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void getAll() {
        Author author1 = Author.builder().id("1").name("Author1").build();
        Author author2 = Author.builder().id("2").name("Author2").build();
        Author author3 = Author.builder().id("3").name("Author3").build();
        Author author4 = Author.builder().id("4").name("Author3").build();
        List<Author> expectedAuthors = List.of(author1, author2, author3, author4);
        List<Author> actualAuthors = authorRepository.findAll();
        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @Test
    void remove() {
        Author newAuthor = authorRepository.save(Author.builder().name("new Author").build());
        Author author = authorRepository.findById(newAuthor.getId()).orElseGet(() -> fail("Author not found!"));
        authorRepository.deleteById(author.getId());
        assertFalse(authorRepository.findById(author.getId()).isPresent());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldNotRemoveFromBookWhenRemoved() {
        Book book = bookRepository.findById("1").orElseGet(() -> fail("Book not found"));
        Author author = authorRepository.findById(book.getGenre().getId()).orElseGet(() -> fail("Author not found!"));
        authorRepository.deleteById(author.getId());
        assertThat(bookRepository.findById("1")).isNotEmpty().get().extracting(Book::getAuthor).isNotNull();
    }

    @Test
    void count() {
        long actualAuthorsCount = authorRepository.count();
        Author author = Author.builder().id("5").name("Author6").build();
        authorRepository.save(author);
        long updatedAuthorsCount = authorRepository.count();
        assertThat(actualAuthorsCount + 1).isEqualTo(updatedAuthorsCount);
    }
}