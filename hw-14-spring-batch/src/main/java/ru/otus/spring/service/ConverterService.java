package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.model.documents.AuthorMongo;
import ru.otus.spring.model.documents.BookMongo;
import ru.otus.spring.model.documents.GenreMongo;
import ru.otus.spring.model.entities.AuthorJpa;
import ru.otus.spring.model.entities.BookJpa;
import ru.otus.spring.model.entities.GenreJpa;

import java.util.HashMap;

@Service
public class ConverterService {
    static HashMap<String, Long> authorIdMap = new HashMap<>();
    static HashMap<String, Long> genreIdMap = new HashMap<>();
    static Long authorCounter = 0L;
    static Long genreCounter = 0L;

    public AuthorJpa convertAuthor(AuthorMongo authorMongo) {
        String authorMongoId = authorMongo.getId();
        authorCounter++;
        authorIdMap.put(authorMongoId, authorCounter);
        return AuthorJpa.builder()
                .id(authorCounter)
                .name(authorMongo.getName())
                .build();
    }

    public GenreJpa convertGenre(GenreMongo genreMongo) {
        String genreMongoId = genreMongo.getId();
        genreCounter++;
        genreIdMap.put(genreMongoId, genreCounter);
        return GenreJpa.builder()
                .id(genreCounter)
                .name(genreMongo.getName())
                .build();
    }

    public BookJpa convertBook(BookMongo bookMongo) {
        String authorMongoId = bookMongo.getAuthorMongo().getId();
        String genreMongoId = bookMongo.getGenreMongo().getId();
        Long authorJpaId = authorIdMap.get(authorMongoId);
        Long genreJpaId = genreIdMap.get(genreMongoId);
        return BookJpa.builder()
                .name(bookMongo.getName())
                .authorJpa(AuthorJpa.builder().id(authorJpaId).build())
                .genreJpa(GenreJpa.builder().id(genreJpaId).build())
                .build();
    }
}
