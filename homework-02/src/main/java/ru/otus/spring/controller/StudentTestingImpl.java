package ru.otus.spring.controller;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.QuestionService;

import java.util.List;

@Service
public class StudentTestingImpl implements StudentTesting {
    private final QuestionService questionService;
    private final IOService ioService;
    private int numberOfCorrectStudentAnswers = 0;

    public StudentTestingImpl(QuestionService questionService, IOService ioService) {
        this.questionService = questionService;
        this.ioService = ioService;
    }

    @Override
    public void startTesting() {
        List<Question> questions = questionService.getQuestions();
        ioService.print("Enter your name: ");
        String name = ioService.read();
        ioService.print("");
        for (Question question : questions) {
            printQuestion(question);
            int studentAnswer = readAnswer();
            checkAnswer(question, studentAnswer);
        }
        ioService.print("The test contained " + questions.size() + " questions");
        ioService.print(name + " you have successfully answered " + getNumberOfCorrectStudentAnswers() + " questions");
    }

    public int getNumberOfCorrectStudentAnswers() {
        return numberOfCorrectStudentAnswers;
    }

    private int readAnswer() {
        ioService.print("\nEnter answer number: ");
        return Integer.parseInt(ioService.read());
    }

    private void checkAnswer(Question question, int studentAnswer) {
        if (studentAnswer == question.getCorrectAnswerNumber()) {
            numberOfCorrectStudentAnswers++;
        }
    }

    private void printQuestion(Question question) {
        ioService.print(question.getText());
        List<String> answers = question.getAnswers();
        for (int i = 0; i < question.getAnswers().size(); i++) {
            ioService.print(i + 1 + ") " + answers.get(i));
        }
    }
}
