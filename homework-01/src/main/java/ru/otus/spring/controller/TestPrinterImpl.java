package ru.otus.spring.controller;

import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;

import java.util.List;

public class TestPrinterImpl implements TestPrinter {
    private final QuestionService service;

    public TestPrinterImpl(QuestionService service) {
        this.service = service;
    }

    @Override
    public void print() {
        List<Question> questions = service.getQuestions();
        for (Question question : questions) {
            System.out.println(question.getText());
            for (int i = 0; i < question.getAnswers().length; i++) {
                System.out.printf("%d) %s\n", i + 1, question.getAnswers()[i].getText());
            }
        }
    }
}
