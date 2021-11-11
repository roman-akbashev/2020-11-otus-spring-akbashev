package ru.otus.spring.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.spring.exceptions.EntityAlreadyExistsException;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.rest.dto.ApiError;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityCanNotBeDeletedException.class)
    public ResponseEntity<Object> handleEntityCanNotBeDeleted(EntityCanNotBeDeletedException ex) {
        return new ResponseEntity<>(new ApiError(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ApiError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExists(EntityAlreadyExistsException ex) {
        return new ResponseEntity<>(new ApiError(ex.getMessage()), HttpStatus.CONFLICT);
    }
}