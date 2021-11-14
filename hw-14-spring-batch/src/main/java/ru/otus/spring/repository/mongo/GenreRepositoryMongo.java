package ru.otus.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.documents.GenreMongo;

public interface GenreRepositoryMongo extends MongoRepository<GenreMongo, String> {

}