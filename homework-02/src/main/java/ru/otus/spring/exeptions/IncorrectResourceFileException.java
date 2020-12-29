package ru.otus.spring.exeptions;

import java.io.IOException;

public class IncorrectResourceFileException extends RuntimeException {

    public IncorrectResourceFileException(String description, IOException cause) {
        super(description, cause);
    }

    public IncorrectResourceFileException(IOException cause) {
        super(cause);
    }

    public IncorrectResourceFileException(String description) {
        super(description);
    }

}
