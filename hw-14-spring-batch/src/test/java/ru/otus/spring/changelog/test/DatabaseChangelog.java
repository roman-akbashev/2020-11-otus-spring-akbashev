package ru.otus.spring.mongock.changelog.test;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.model.documents.AuthorMongo;
import ru.otus.spring.model.documents.BookMongo;
import ru.otus.spring.model.documents.GenreMongo;
import ru.otus.spring.repository.mongo.AuthorRepositoryMongo;
import ru.otus.spring.repository.mongo.BookRepositoryMongo;
import ru.otus.spring.repository.mongo.GenreRepositoryMongo;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    private AuthorMongo swiftAuthor;

    private GenreMongo satireGenre;

    @ChangeSet(order = "000", id = "dropDb", author = "akbashev", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "addAuthors", author = "akbashev", runAlways = true)
    public void addAuthors(AuthorRepositoryMongo repository) {
        swiftAuthor = repository.save(AuthorMongo.builder().name("Swift").build());
        repository.save(AuthorMongo.builder().name("Dostoevsky").build());
        repository.save(AuthorMongo.builder().name("Goethe").build());
    }

    @ChangeSet(order = "002", id = "addGenres", author = "akbashev", runAlways = true)
    public void addGenres(GenreRepositoryMongo repository) {
        satireGenre = repository.save(GenreMongo.builder().name("Satire").build());
        repository.save(GenreMongo.builder().name("Novel").build());
        repository.save(GenreMongo.builder().name("Tragedy").build());
    }

    @ChangeSet(order = "003", id = "addBooks", author = "akbashev", runAlways = true)
    public void addBooks(BookRepositoryMongo repository) {
        repository.save(BookMongo.builder().name("Gulliver").authorMongo(swiftAuthor).genreMongo(satireGenre).build());
    }
}