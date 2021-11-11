package ru.otus.spring.exceptions;

public class EntityAlreadyExistsException extends LibraryAppException {

    public EntityAlreadyExistsException(String description) {
        super(description);
    }

}
