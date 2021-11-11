package ru.otus.spring.domain;

import java.util.List;

public class Question {
    private final String text;
    private final List<String> answers;
    private final int correctAnswerNumber;

    public Question(String text, List<String> answers, int correctAnswerNumber) {
        this.text = text;
        this.answers = answers;
        this.correctAnswerNumber = correctAnswerNumber;
    }

    public String getText() {
        return text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }
}