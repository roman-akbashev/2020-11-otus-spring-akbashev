package ru.otus.spring.testchangelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.domain.*;
import ru.otus.spring.repositories.*;

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
        repository.save(Author.builder().id("3").name("Author3").build());
        repository.save(Author.builder().id("4").name("Author3").build());
    }

    @ChangeSet(order = "002", id = "addGenres", author = "akbashev", runAlways = true)
    public void addGenres(GenreRepository repository) {
        genre1 = repository.save(Genre.builder().id("1").name("Genre1").build());
        genre2 = repository.save(Genre.builder().id("2").name("Genre2").build());
        repository.save(Genre.builder().id("3").name("Genre3").build());
        repository.save(Genre.builder().id("4").name("Genre3").build());
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

    @ChangeSet(order = "005", id = "addUsers", author = "akbashev", runAlways = true)
    public void users(UserRepository userRepository) {
        userRepository.save(new User("1", "admin", "password"));
        userRepository.save(new User("2", "roman", "12345"));
    }
}
