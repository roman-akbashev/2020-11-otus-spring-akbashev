package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class GenreCascadeDeleteEventsListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        String id = source.get("_id").toString();
        if (bookRepository.checkExistsByAuthorId(id)) {
            throw new EntityCanNotBeDeletedException("Genre with id " + id + " can not be deleted because there is a link with the book");
        }
    }
}

