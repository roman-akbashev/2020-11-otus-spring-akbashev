package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    private Long id;
    private String name;
    private Author author;
    private Genre genre;
}
