package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

@Component
@RequiredArgsConstructor
public class AuthorCascadeUpdateEventsListener extends AbstractMongoEventListener<Author> {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public void onAfterSave(AfterSaveEvent<Author> event) {
        super.onAfterSave(event);
        Author author = event.getSource();
        Query query = new Query(Criteria.where("author.id").is(author.getId()));
        Update update = new Update().set("author.name", author.getName());
        mongoTemplate.updateMulti(query, update, Book.class).subscribe();
    }
}

