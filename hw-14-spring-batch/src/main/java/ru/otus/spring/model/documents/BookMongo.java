package ru.otus.spring.model.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class BookMongo {
    @Id
    private long id;

    private String name;

    @DBRef
    private AuthorMongo authorMongo;

    @DBRef
    private GenreMongo genreMongo;
}