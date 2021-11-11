package ru.otus.spring.exceptions;

public class EntityCanNotBeDeletedException extends BookLibraryAppExceptions {

    public EntityCanNotBeDeletedException(String description) {
        super(description);
    }

}
