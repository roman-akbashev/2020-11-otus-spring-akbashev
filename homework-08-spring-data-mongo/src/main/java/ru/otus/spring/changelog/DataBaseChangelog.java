package ru.otus.spring.changelog;

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
    private Author swiftAuthor;
    private Author dostoevskyAuthor;
    private Author goetheAuthor;

    private Genre satireGenre;
    private Genre novelGenre;
    private Genre tragedyGenre;

    private Book gulliverBook;

    @ChangeSet(order = "000", id = "dropDb", author = "akbashev", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "addAuthors", author = "akbashev", runAlways = true)
    public void addAuthors(AuthorRepository repository) {
        swiftAuthor = repository.save(Author.builder().name("Swift").build());
        dostoevskyAuthor = repository.save(Author.builder().name("Dostoevsky").build());
        goetheAuthor = repository.save(Author.builder().name("Goethe").build());
    }

    @ChangeSet(order = "002", id = "addGenres", author = "akbashev", runAlways = true)
    public void addGenres(GenreRepository repository) {
        satireGenre = repository.save(Genre.builder().name("Satire").build());
        novelGenre = repository.save(Genre.builder().name("Novel").build());
        tragedyGenre = repository.save(Genre.builder().name("Tragedy").build());
    }

    @ChangeSet(order = "003", id = "addBooks", author = "akbashev", runAlways = true)
    public void addBooks(BookRepository repository) {
        gulliverBook = repository.save(Book.builder().name("Gulliver").author(swiftAuthor).genre(satireGenre).build());
        repository.save(Book.builder().name("Idiot").author(dostoevskyAuthor).genre(novelGenre).build());
        repository.save(Book.builder().name("Faust").author(goetheAuthor).genre(tragedyGenre).build());
    }

    @ChangeSet(order = "004", id = "addComments", author = "akbashev", runAlways = true)
    public void addComments(CommentRepository repository) {
        repository.save(Comment.builder().commentText("book comment 1").book(gulliverBook).build());
        repository.save(Comment.builder().commentText("book comment 2").book(gulliverBook).build());
        repository.save(Comment.builder().commentText("book comment 3").book(gulliverBook).build());
    }
}
