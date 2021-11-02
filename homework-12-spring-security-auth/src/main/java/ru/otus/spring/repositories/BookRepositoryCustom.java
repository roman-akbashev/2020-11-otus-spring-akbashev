package ru.otus.spring.repositories;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

public interface BookRepositoryCustom {

    boolean checkExistsByAuthorId(String authorId);

    boolean checkExistsByGenreId(String genreId);

    void updateAuthorInBooks(Author author);

    void updateGenreInBooks(Genre genre);
}
