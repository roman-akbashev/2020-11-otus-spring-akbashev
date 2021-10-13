package ru.otus.spring.exceptions;

public class EntityCanNotBeDeletedException extends LibraryAppException {

    public EntityCanNotBeDeletedException(String description) {
        super(description);
    }

}
