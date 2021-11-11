package ru.otus.spring.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String description) {
        super(description);
    }

}
