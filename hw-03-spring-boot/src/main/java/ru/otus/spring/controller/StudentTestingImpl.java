package ru.otus.spring.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.AppProperties;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.QuestionService;

import java.util.List;

@Service
public class StudentTestingImpl implements StudentTesting {
    private final QuestionService questionService;
    private final IOService ioService;
    private final MessageSource messageSource;
    private final AppProperties appProperties;
    private int numberOfCorrectStudentAnswers = 0;

    public StudentTestingImpl(QuestionService questionService, IOService ioService, MessageSource messageSource,
                              AppProperties appProperties) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.appProperties = appProperties;
    }

    @Override
    public void startTesting() {
        List<Question> questions = questionService.getQuestions();
        var enterNameMsg = getMessage("enter.student.name", null);
        ioService.print(enterNameMsg);
        var name = ioService.read();
        for (Question question : questions) {
            printQuestion(question);
            int studentAnswer = readAnswer();
            checkAnswer(question, studentAnswer);
        }
        var testQuestionsSize = getMessage("test.questions.size", questions.size());
        ioService.print(testQuestionsSize);
        var successfullyAnsweredQuestions = getMessage("successfully.answered.questions",
                numberOfCorrectStudentAnswers);
        ioService.print(name + successfullyAnsweredQuestions);
    }

    private int readAnswer() {
        var enterAnswerNumber = getMessage("enter.answer.number", null);
        ioService.print(enterAnswerNumber);
        return Integer.parseInt(ioService.read());
    }

    private void checkAnswer(Question question, int studentAnswer) {
        if (studentAnswer == question.getCorrectAnswerNumber()) {
            numberOfCorrectStudentAnswers++;
        }
    }

    private void printQuestion(Question question) {
        ioService.print("\n" + question.getText());
        var answers = question.getAnswers();
        for (int i = 0; i < question.getAnswers().size(); i++) {
            ioService.print(i + 1 + ") " + answers.get(i));
        }
    }

    private String getMessage(String bundleParamName, Object externalParam) {
        return messageSource.getMessage(bundleParamName, new Object[]{externalParam}, appProperties.getLocale());
    }
}
