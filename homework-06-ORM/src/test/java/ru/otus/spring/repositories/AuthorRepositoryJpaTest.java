package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

@JdbcTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void insert() {
        Author expectedAuthor = Author.builder().id(5L).name("Author4").build();
        authorRepository.save(expectedAuthor);
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
    void getById() {
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
    void deleteById() {
        final long authorForDeleteId = 4L;
        authorRepository.remove(Author.builder().id(authorForDeleteId).build());
        assertFalse(authorRepository.findById(4L).isPresent());
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