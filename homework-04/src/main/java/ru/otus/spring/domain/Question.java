package ru.otus.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Question {
    @Getter
    private final String text;
    @Getter
    private final List<String> answers;
    @Getter
    private final int correctAnswerNumber;

}