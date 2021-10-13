package ru.otus.spring.exceptions;

public class EntityNotFoundException extends LibraryAppException {

    public EntityNotFoundException(String description) {
        super(description);
    }

}
