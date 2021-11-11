package ru.otus.spring.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.spring.AbstractRepositoryTest;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.events.GenreCascadeDeleteEventsListener;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@Import(GenreCascadeDeleteEventsListener.class)
class GenreRepositoryWithListenerTest extends AbstractRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldThrowExceptionWhenRemove() {
        Book book = bookRepository.findById("1").orElseGet(() -> fail("Book not found"));
        Genre genre = genreRepository.findById(book.getGenre().getId()).orElseGet(() -> fail("Genre not found!"));
        EntityCanNotBeDeletedException exception = assertThrows(EntityCanNotBeDeletedException.class, () -> genreRepository.deleteById(genre.getId()));
        Assertions.assertEquals("Genre with id " + genre.getId() + " can not be deleted because there is a link with the book", exception.getMessage());
    }
}