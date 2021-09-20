package ru.otus.spring.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String description) {
        super(description);
    }

}
