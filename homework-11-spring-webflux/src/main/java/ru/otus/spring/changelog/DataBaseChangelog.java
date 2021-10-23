package ru.otus.spring.changelog;

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
    private Author swiftAuthor;
    private Author dostoevskyAuthor;
    private Author goetheAuthor;

    private Genre satireGenre;
    private Genre novelGenre;
    private Genre tragedyGenre;

    private Book gulliverBook;

    @ChangeSet(order = "001", id = "dropDb", author = "akbashev", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "akbashev", runAlways = true)
    public void addAuthors(MongoOperations mongoOperations) {
        swiftAuthor = mongoOperations.insert(Author.builder().name("Swift").build());
        dostoevskyAuthor = mongoOperations.insert(Author.builder().name("Dostoevsky").build());
        goetheAuthor = mongoOperations.insert(Author.builder().name("Goethe").build());
    }

    @ChangeSet(order = "003", id = "addGenres", author = "akbashev", runAlways = true)
    public void addGenres(MongoOperations mongoOperations) {
        satireGenre = mongoOperations.insert(Genre.builder().name("Satire").build());
        novelGenre = mongoOperations.insert(Genre.builder().name("Novel").build());
        tragedyGenre = mongoOperations.insert(Genre.builder().name("Tragedy").build());
    }

    @ChangeSet(order = "004", id = "addBooks", author = "akbashev", runAlways = true)
    public void addBooks(MongoOperations mongoOperations) {
        gulliverBook = mongoOperations.insert(Book.builder().name("Gulliver").author(swiftAuthor).genre(satireGenre).build());
        mongoOperations.insert(Book.builder().name("Idiot").author(dostoevskyAuthor).genre(novelGenre).build());
        mongoOperations.insert(Book.builder().name("Faust").author(goetheAuthor).genre(tragedyGenre).build());
    }

    @ChangeSet(order = "005", id = "addComments", author = "akbashev", runAlways = true)
    public void addComments(MongoOperations mongoOperations) {
        mongoOperations.insert(Comment.builder().commentText("book comment 1").book(gulliverBook).build());
        mongoOperations.insert(Comment.builder().commentText("book comment 2").book(gulliverBook).build());
        mongoOperations.insert(Comment.builder().commentText("book comment 3").book(gulliverBook).build());
    }
}
