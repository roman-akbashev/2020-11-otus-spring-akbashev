package ru.otus.spring.exceptions;

public class LibraryAppException extends RuntimeException {

    public LibraryAppException(String description) {
        super(description);
    }
}
