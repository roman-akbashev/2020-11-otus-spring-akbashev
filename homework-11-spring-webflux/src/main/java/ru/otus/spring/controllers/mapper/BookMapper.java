package ru.otus.spring.controllers.mapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.controllers.dto.AuthorDto;
import ru.otus.spring.controllers.dto.BookDto;
import ru.otus.spring.controllers.dto.GenreDto;

@RequiredArgsConstructor
@Component
public class BookMapper implements DtoMapper<Book, BookDto> {
    private final DtoMapper<Author, AuthorDto> authorMapper;
    private final DtoMapper<Genre, GenreDto> genreMapper;

    @Override
    public Book toEntity(@NonNull BookDto dto) {
        return
                new Book(
                        dto.getId(),
                        dto.getName(),
                        authorMapper.toEntity(dto.getAuthor()),
                        genreMapper.toEntity(dto.getGenre())
                );
    }

    @Override
    public BookDto toDto(@NonNull Book book) {
        return
                new BookDto(
                        book.getId(),
                        book.getName(),
                        authorMapper.toDto(book.getAuthor()),
                        genreMapper.toDto(book.getGenre())
                );
    }
}
