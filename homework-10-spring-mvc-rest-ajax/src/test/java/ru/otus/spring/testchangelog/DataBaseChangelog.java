package ru.otus.spring.testchangelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;
import ru.otus.spring.repositories.GenreRepository;

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
    public void addAuthors(AuthorRepository repository) {
        author1 = repository.save(Author.builder().id("1").name("Author1").build());
        author2 = repository.save(Author.builder().id("2").name("Author2").build());
    }

    @ChangeSet(order = "002", id = "addGenres", author = "akbashev", runAlways = true)
    public void addGenres(GenreRepository repository) {
        genre1 = repository.save(Genre.builder().id("1").name("Genre1").build());
        genre2 = repository.save(Genre.builder().id("2").name("Genre2").build());
    }

    @ChangeSet(order = "003", id = "addBooks", author = "akbashev", runAlways = true)
    public void addBooks(BookRepository repository) {
        book1 = repository.save(Book.builder().id("1").name("Book1").author(author1).genre(genre1).build());
        repository.save(Book.builder().id("2").name("Book2").author(author2).genre(genre2).build());
    }

    @ChangeSet(order = "004", id = "addComments", author = "akbashev", runAlways = true)
    public void addComments(CommentRepository repository) {
        repository.save(Comment.builder().id("1").commentText("book comment 1").book(book1).build());
        repository.save(Comment.builder().id("2").commentText("book comment 2").book(book1).build());
        repository.save(Comment.builder().id("3").commentText("book comment 3").book(book1).build());
    }
}
