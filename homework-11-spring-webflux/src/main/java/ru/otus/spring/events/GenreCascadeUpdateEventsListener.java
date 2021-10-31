package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@Component
@RequiredArgsConstructor
public class GenreCascadeUpdateEventsListener extends AbstractMongoEventListener<Genre> {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public void onAfterSave(AfterSaveEvent<Genre> event) {
        super.onAfterSave(event);
        Genre genre = event.getSource();
        Query query = new Query(Criteria.where("genre.id").is(genre.getId()));
        Update update = new Update().set("genre.name", genre.getName());
        mongoTemplate.updateMulti(query, update, Book.class).subscribe();
    }
}

