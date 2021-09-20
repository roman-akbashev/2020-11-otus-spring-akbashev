package ru.otus.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.Book;

@Data
@AllArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private AuthorDto author;
    private GenreDto genre;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName(), AuthorDto.toDto(book.getAuthor()), GenreDto.toDto(book.getGenre()));
    }
}
