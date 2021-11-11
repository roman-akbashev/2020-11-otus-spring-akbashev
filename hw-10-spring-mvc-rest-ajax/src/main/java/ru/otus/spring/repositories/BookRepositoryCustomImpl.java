package ru.otus.spring.repositories;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@AllArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public boolean checkExistsByAuthorId(String authorId) {
        Query searchQuery = new Query(Criteria.where("author.id").is(authorId));
        return mongoTemplate.exists(searchQuery, Book.class);
    }

    @Override
    public boolean checkExistsByGenreId(String genreId) {
        Query searchQuery = new Query(Criteria.where("genre.id").is(genreId));
        return mongoTemplate.exists(searchQuery, Book.class);
    }

    @Override
    public void updateAuthorInBooks(Author author) {
        Query query = new Query(Criteria.where("author.id").is(author.getId()));
        Update update = new Update().set("author.name", author.getName());
        mongoTemplate.updateMulti(query, update, Book.class);
    }

    @Override
    public void updateGenreInBooks(Genre genre) {
        Query query = new Query(Criteria.where("genre.id").is(genre.getId()));
        Update update = new Update().set("genre.name", genre.getName());
        mongoTemplate.updateMulti(query, update, Book.class);
    }
}
