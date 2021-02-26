package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookServiceImpl.class)
class BookServiceImplTest {
    private static final long AUTHOR_ID = 1L;
    private static final long BOOK_ID = 1L;
    private static final String BOOK_NAME = "Idiot";
    private static final long GENRE_ID = 1L;
    private static final String AUTHOR_NAME = "Dostoevsky";
    private static final long BOOK_COUNT = 2L;

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    void shouldThrowExceptionIfAuthorNotFoundForCreate() {
        Mockito.when(authorService.getById(AUTHOR_ID)).thenThrow(
                new EntityNotFoundException("Author with id " + AUTHOR_ID + " not found!"));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.create(BOOK_NAME, AUTHOR_ID, GENRE_ID));
        Assertions.assertEquals("Author with id " + AUTHOR_ID + " not found!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfGenreNotFoundForCreate() {
        Mockito.when(authorService.getById(AUTHOR_ID)).thenReturn(Author.builder().id(AUTHOR_ID).name(AUTHOR_NAME).build());
        Mockito.when(genreService.getById(GENRE_ID)).thenThrow(
                new EntityNotFoundException("Genre with id " + GENRE_ID + " not found!"));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.create(BOOK_NAME, AUTHOR_ID, GENRE_ID));
        Assertions.assertEquals("Genre with id " + GENRE_ID + " not found!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfAuthorNotFoundForUpdate() {
        Mockito.when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.ofNullable(Book.builder().id(BOOK_ID).build()));
        Mockito.when(authorService.getById(AUTHOR_ID)).thenThrow(
                new EntityNotFoundException("Author with id " + AUTHOR_ID + " not found!"));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.update(BOOK_ID, BOOK_NAME, AUTHOR_ID, GENRE_ID));
        Assertions.assertEquals("Author with id " + AUTHOR_ID + " not found!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfGenreNotFoundForUpdate() {
        Mockito.when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.ofNullable(Book.builder().id(BOOK_ID).build()));
        Mockito.when(authorService.getById(AUTHOR_ID)).thenReturn(Author.builder().id(AUTHOR_ID).name(AUTHOR_NAME).build());
        Mockito.when(genreService.getById(GENRE_ID)).thenThrow(
                new EntityNotFoundException("Genre with id " + GENRE_ID + " not found!"));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.update(BOOK_ID, BOOK_NAME, AUTHOR_ID, GENRE_ID));
        Assertions.assertEquals("Genre with id " + GENRE_ID + " not found!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfBookNotFoundForUpdate() {
        Mockito.when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.update(BOOK_ID, BOOK_NAME, AUTHOR_ID, GENRE_ID));
        Assertions.assertEquals("Book with id " + BOOK_ID + " not found!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfBookNotFoundForRead() {
        Mockito.when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> bookService.read(BOOK_ID));
        Assertions.assertEquals("Book with id " + BOOK_ID + " not found!", exception.getMessage());
    }

    @Test
    void shouldReturnCorrectBookCount() {
        Mockito.when(bookRepository.count()).thenReturn(BOOK_COUNT);
        String actual = bookService.count();
        Assertions.assertEquals(String.valueOf(BOOK_COUNT), actual);
    }

    @Test
    void create() {
        Mockito.when(authorService.getById(AUTHOR_ID)).thenReturn(Author.builder().id(AUTHOR_ID).build());
        Mockito.when(genreService.getById(GENRE_ID)).thenReturn(Genre.builder().id(GENRE_ID).build());
        Book book = Book.builder().
                name(BOOK_NAME).
                author(Author.builder().id(AUTHOR_ID).build()).
                genre(Genre.builder().id(GENRE_ID).build()).
                build();
        bookService.create(BOOK_NAME, AUTHOR_ID, GENRE_ID);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void update() {
        Mockito.when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.ofNullable(Book.builder().id(BOOK_ID).build()));
        Mockito.when(authorService.getById(AUTHOR_ID)).thenReturn(Author.builder().id(AUTHOR_ID).build());
        Mockito.when(genreService.getById(GENRE_ID)).thenReturn(Genre.builder().id(GENRE_ID).build());
        Book book = Book.builder().
                id(BOOK_ID).
                name(BOOK_NAME).
                author(Author.builder().id(AUTHOR_ID).build()).
                genre(Genre.builder().id(GENRE_ID).build()).
                build();
        bookService.update(BOOK_ID, BOOK_NAME, AUTHOR_ID, GENRE_ID);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void readAll() {
        bookService.readAll();
        verify(bookRepository, times(1)).findAll();
    }
}