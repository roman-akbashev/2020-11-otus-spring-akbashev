package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.repositories.AuthorRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {
    private static final long AUTHOR_ID = 1L;
    private static final String AUTHOR_NAME = "Dostoevsky";

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void create() {
        Author author = Author.builder().name(AUTHOR_NAME).build();
        authorService.create(AUTHOR_NAME);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void update() {
        Mockito.when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.ofNullable(Author.builder().id(AUTHOR_ID).build()));
        Author author = Author.builder().id(AUTHOR_ID).name(AUTHOR_NAME).build();
        authorService.update(AUTHOR_ID, AUTHOR_NAME);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void readAll() {
        authorService.readAll();
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void readByName() {
        authorService.readByName(AUTHOR_NAME);
        verify(authorRepository, times(1)).findByName(AUTHOR_NAME);
    }

    @Test
    void deleteById() {
        Mockito.when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.ofNullable(Author.builder().id(AUTHOR_ID).build()));
        Mockito.when(authorRepository.checkRelatedBookByAuthorId(AUTHOR_ID)).thenReturn(false);
        authorService.deleteById(AUTHOR_ID);
        verify(authorRepository, times(1)).deleteById(AUTHOR_ID);
    }

    @Test
    void deleteByIdWithException() {
        Mockito.when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.ofNullable(Author.builder().id(AUTHOR_ID).build()));
        Mockito.when(authorRepository.checkRelatedBookByAuthorId(AUTHOR_ID)).thenReturn(true);
        EntityCanNotBeDeletedException exception = assertThrows(EntityCanNotBeDeletedException.class, () -> authorService.deleteById(AUTHOR_ID));
        Assertions.assertEquals("Author with id " + AUTHOR_ID + " can not be deleted because there is a link with the book", exception.getMessage());
    }

    @Test
    void getById() {
        Mockito.when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.ofNullable(Author.builder().id(AUTHOR_ID).build()));
        authorService.getById(AUTHOR_ID);
        verify(authorRepository, times(1)).findById(AUTHOR_ID);
    }

    @Test
    void count() {
        authorService.count();
        verify(authorRepository, times(1)).count();
    }
}