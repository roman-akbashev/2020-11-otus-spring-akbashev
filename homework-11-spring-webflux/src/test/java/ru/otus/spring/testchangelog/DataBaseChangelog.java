package ru.otus.spring.testchangelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

@ChangeLog(order = "001")
public class DataBaseChangelog {
    private Author author1;
    private Author author2;

    private Genre genre1;
    private Genre genre2;

    private Book book1;

    @ChangeSet(order = "000", id = "dropDb", author = "akbashev", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "addAuthors", author = "akbashev", runAlways = true)
    public void addAuthors(MongoOperations mongoOperations) {
        author1 = mongoOperations.insert(Author.builder().id("1").name("Author1").build());
        author2 = mongoOperations.insert(Author.builder().id("2").name("Author2").build());
    }

    @ChangeSet(order = "002", id = "addGenres", author = "akbashev", runAlways = true)
    public void addGenres(MongoOperations mongoOperations) {
        genre1 = mongoOperations.insert(Genre.builder().id("1").name("Genre1").build());
        genre2 = mongoOperations.insert(Genre.builder().id("2").name("Genre2").build());
    }

    @ChangeSet(order = "003", id = "addBooks", author = "akbashev", runAlways = true)
    public void addBooks(MongoOperations mongoOperations) {
        book1 = mongoOperations.insert(Book.builder().id("1").name("Book1").author(author1).genre(genre1).build());
        mongoOperations.insert(Book.builder().id("2").name("Book2").author(author2).genre(genre2).build());
    }

    @ChangeSet(order = "004", id = "addComments", author = "akbashev", runAlways = true)
    public void addComments(MongoOperations mongoOperations) {
        mongoOperations.insert(Comment.builder().id("1").commentText("book comment 1").book(book1).build());
        mongoOperations.insert(Comment.builder().id("2").commentText("book comment 2").book(book1).build());
        mongoOperations.insert(Comment.builder().id("3").commentText("book comment 3").book(book1).build());
    }
}
