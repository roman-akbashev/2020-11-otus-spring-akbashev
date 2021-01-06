package ru.otus.spring.exceptions;

import java.io.IOException;

public class IncorrectRecordFormatException extends RuntimeException {

    public IncorrectRecordFormatException(String description, IOException cause) {
        super(description, cause);
    }

    public IncorrectRecordFormatException(IOException cause) {
        super(cause);
    }

    public IncorrectRecordFormatException(String description) {
        super(description);
    }

}
