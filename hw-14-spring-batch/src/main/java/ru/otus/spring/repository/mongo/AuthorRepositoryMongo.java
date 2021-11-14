package ru.otus.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.documents.AuthorMongo;

public interface AuthorRepositoryMongo extends MongoRepository<AuthorMongo, String> {

}