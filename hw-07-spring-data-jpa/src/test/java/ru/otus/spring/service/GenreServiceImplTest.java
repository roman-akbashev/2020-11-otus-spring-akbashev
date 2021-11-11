package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.repositories.GenreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GenreServiceImpl.class)
class GenreServiceImplTest {
    private static final long GENRE_ID = 1L;
    private static final String GENRE_NAME = "Tragedy";

    @Autowired
    private GenreService genreService;

    @MockBean
    private GenreRepository genreRepository;


    @Test
    void create() {
        Genre genre = Genre.builder().name(GENRE_NAME).build();
        genreService.create(GENRE_NAME);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    void update() {
        Mockito.when(genreRepository.findById(GENRE_ID)).thenReturn(Optional.ofNullable(Genre.builder().id(GENRE_ID).build()));
        Genre genre = Genre.builder().id(GENRE_ID).name(GENRE_NAME).build();
        genreService.update(GENRE_ID, GENRE_NAME);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    void readAll() {
        genreService.readAll();
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void readByName() {
        genreService.readByName(GENRE_NAME);
        verify(genreRepository, times(1)).findByName(GENRE_NAME);
    }

    @Test
    void deleteById() {
        Mockito.when(genreRepository.findById(GENRE_ID)).thenReturn(Optional.ofNullable(Genre.builder().id(GENRE_ID).build()));
        Mockito.when(genreRepository.checkRelatedBookByGenreId(GENRE_ID)).thenReturn(false);
        genreService.deleteById(GENRE_ID);
        verify(genreRepository, times(1)).deleteById(GENRE_ID);
    }

    @Test
    void deleteByIdWithException() {
        Mockito.when(genreRepository.findById(GENRE_ID)).thenReturn(Optional.ofNullable(Genre.builder().id(GENRE_ID).build()));
        Mockito.when(genreRepository.checkRelatedBookByGenreId(GENRE_ID)).thenReturn(true);
        EntityCanNotBeDeletedException exception = assertThrows(EntityCanNotBeDeletedException.class, () -> genreService.deleteById(GENRE_ID));
        Assertions.assertEquals("Genre with id " + GENRE_ID + " can not be deleted because there is a link with the book", exception.getMessage());
    }

    @Test
    void getById() {
        Mockito.when(genreRepository.findById(GENRE_ID)).thenReturn(Optional.ofNullable(Genre.builder().id(GENRE_ID).build()));
        genreService.getById(GENRE_ID);
        verify(genreRepository, times(1)).findById(GENRE_ID);
    }

    @Test
    void count() {
        genreService.count();
        verify(genreRepository, times(1)).count();
    }
}