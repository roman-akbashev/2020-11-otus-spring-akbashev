package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.IncorrectRecordFormatException;
import ru.otus.spring.util.QuestionReader;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoCsv implements QuestionDao {
    private final QuestionReader questionReader;

    public QuestionDaoCsv(QuestionReader questionReader) {
        this.questionReader = questionReader;
    }

    @Override
    public List<Question> getAllQuestions() {
        return readQuestionsFromCsv();
    }

    private List<Question> readQuestionsFromCsv() {
        List<Question> questions = new ArrayList<>();
        List<List<String>> records = questionReader.getRecords();
        for (List<String> record : records) {
            int recordSize = record.size();
            String questionText = record.get(0);
            List<String> answers = new ArrayList<>();
            for (int i = 1; i < recordSize - 1; i++) {
                answers.add(record.get(i));
            }
            int correctAnswerNumber;
            try{
                correctAnswerNumber = Integer.parseInt(record.get(recordSize - 1));
            } catch (NumberFormatException e){
                throw new IncorrectRecordFormatException("The record does not contain the number of the answer to the question");
            }
            questions.add(new Question(questionText, answers, correctAnswerNumber));
        }
        return questions;
    }
}