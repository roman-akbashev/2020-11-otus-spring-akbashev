package ru.otus.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.documents.BookMongo;

public interface BookRepositoryMongo extends MongoRepository<BookMongo, String> {

}