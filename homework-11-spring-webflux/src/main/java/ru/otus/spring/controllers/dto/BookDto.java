package ru.otus.spring.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private String id;
    private String name;
    private String authorId;
    private String authorName;
    private String genreId;
    private String genreName;
}
