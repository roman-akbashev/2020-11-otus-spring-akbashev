package ru.otus.spring.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.spring.AbstractRepositoryTest;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.events.AuthorCascadeDeleteEventsListener;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@Import(AuthorCascadeDeleteEventsListener.class)
class AuthorRepositoryWithListenerTest extends AbstractRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldThrowExceptionWhenRemove() {
        Book book = bookRepository.findById("1").orElseGet(() -> fail("Book not found"));
        Author author = authorRepository.findById(book.getGenre().getId()).orElseGet(() -> fail("Author not found!"));
        EntityCanNotBeDeletedException exception = assertThrows(EntityCanNotBeDeletedException.class, () -> authorRepository.deleteById(author.getId()));
        Assertions.assertEquals("Author with id " + author.getId() + " can not be deleted because there is a link with the book", exception.getMessage());
    }
}