package ru.otus.spring.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {
    private final Resource questionsResource;

    public QuestionDaoImpl(String questionsResourceName) {
        this.questionsResource = new ClassPathResource(questionsResourceName);
    }

    @Override
    public List<Question> getAllQuestions() {
        return readQuestionsFromCsv();
    }

    private List<Question> readQuestionsFromCsv() {
        List<Question> questions = new ArrayList<>();
        try (Reader reader = new InputStreamReader(questionsResource.getInputStream())) {
            CSVParser csvParser = CSVFormat.EXCEL.withDelimiter(";".charAt(0)).withFirstRecordAsHeader().parse(reader);
            List<CSVRecord> records = csvParser.getRecords();
            for (CSVRecord record : records) {
                String text = record.get("question");
                Answer[] answers = new Answer[3];
                for (int i = 0; i < answers.length; i++) {
                    answers[i] = new Answer(record.get(i + 1 + "answer"));
                }
                questions.add(new Question(text, answers));
            }
            return questions;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}