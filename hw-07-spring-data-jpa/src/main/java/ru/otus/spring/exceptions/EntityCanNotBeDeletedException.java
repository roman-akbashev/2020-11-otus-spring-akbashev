package ru.otus.spring.exceptions;

public class EntityCanNotBeDeletedException extends RuntimeException {

    public EntityCanNotBeDeletedException(String description) {
        super(description);
    }

}
