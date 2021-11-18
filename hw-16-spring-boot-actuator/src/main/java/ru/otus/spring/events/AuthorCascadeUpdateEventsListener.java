package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class AuthorCascadeUpdateEventsListener extends AbstractMongoEventListener<Author> {
    private final BookRepository bookRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Author> event) {
        super.onAfterSave(event);
        Author author = event.getSource();
        bookRepository.updateAuthorInBooks(author);
    }
}

