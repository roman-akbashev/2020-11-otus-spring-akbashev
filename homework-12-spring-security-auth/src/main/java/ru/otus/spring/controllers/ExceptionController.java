package ru.otus.spring.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.spring.exceptions.BookLibraryAppExceptions;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BookLibraryAppExceptions.class)
    public String handleRE(BookLibraryAppExceptions e, Model model) {
        model.addAttribute("exception", e.getMessage());
        return "errorExp";
    }
}