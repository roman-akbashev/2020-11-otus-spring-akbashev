package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void insert() {
        Author expectedAuthor = authorRepository.save(Author.builder().name("Author4").build());
        Author actualAuthor = authorRepository.findById(expectedAuthor.getId()).orElseGet(() -> fail("Author not found!"));
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void update() {
        Author expectedAuthor = Author.builder().id(1L).name("Author5").build();
        authorRepository.save(expectedAuthor);
        Author actualAuthor = authorRepository.findById(expectedAuthor.getId()).orElseGet(() -> fail("Author not found!"));
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void findById() {
        Author expectedAuthor = Author.builder().id(2L).name("Author2").build();
        Author actualAuthor = authorRepository.findById(expectedAuthor.getId()).orElseGet(() -> fail("Author not found!"));
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void getByName() {
        Author author1 = Author.builder().id(3L).name("Author3").build();
        Author author2 = Author.builder().id(4L).name("Author3").build();
        List<Author> expectedAuthors = List.of(author1, author2);
        List<Author> actualAuthorsByName = authorRepository.findByName(author1.getName());
        assertThat(actualAuthorsByName).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @Test
    void checkRelatedBook() {
        assertFalse(authorRepository.checkRelatedBookByAuthor(Author.builder().id(4L).build()));
        assertTrue(authorRepository.checkRelatedBookByAuthor(Author.builder().id(1L).build()));
    }

    @Test
    void getAll() {
        Author author1 = Author.builder().id(1L).name("Author1").build();
        Author author2 = Author.builder().id(2L).name("Author2").build();
        Author author3 = Author.builder().id(3L).name("Author3").build();
        Author author4 = Author.builder().id(4L).name("Author3").build();
        List<Author> expectedAuthors = List.of(author1, author2, author3, author4);
        List<Author> actualAuthors = authorRepository.findAll();
        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @Test
    void remove() {
        final long authorForDeleteId = 4L;
        Author author = authorRepository.findById(authorForDeleteId).orElseGet(() -> fail("Author not found!"));
        authorRepository.remove(author);
        assertFalse(authorRepository.findById(authorForDeleteId).isPresent());
    }

    @Test
    void count() {
        long actualAuthorsCount = authorRepository.count();
        Author author = Author.builder().id(5L).name("Author6").build();
        authorRepository.save(author);
        long updatedAuthorsCount = authorRepository.count();
        assertThat(actualAuthorsCount + 1L).isEqualTo(updatedAuthorsCount);
    }
}