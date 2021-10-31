package ru.otus.spring.controllers.mapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.controllers.dto.BookDto;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@RequiredArgsConstructor
@Component
public class BookMapper implements DtoMapper<Book, BookDto> {

    @Override
    public Book toEntity(@NonNull BookDto dto) {
        return
                new Book(
                        dto.getId(),
                        dto.getName(),
                        new Author(dto.getAuthorId(), dto.getAuthorName()),
                        new Genre(dto.getGenreId(), dto.getGenreName())
                );
    }

    @Override
    public BookDto toDto(@NonNull Book book) {
        return
                new BookDto(
                        book.getId(),
                        book.getName(),
                        book.getAuthor().getId(),
                        book.getAuthor().getName(),
                        book.getGenre().getId(),
                        book.getGenre().getName()
                );
    }
}
