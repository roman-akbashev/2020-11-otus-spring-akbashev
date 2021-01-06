package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.exceptions.IncorrectRecordFormatException;
import ru.otus.spring.util.QuestionReader;
import ru.otus.spring.util.QuestionsCsvReader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование QuestionDao")
public class QuestionDaoCsvTest {

    @DisplayName("Проверим, что если запись возвращаемая ридером не содержит в конце номера корректного ответа сгенерируется исключение")
    @Test
    public void shouldBeThrownAnExceptionWhenTheReaderRecordWithoutCorrectAnswerNumber() {
        List<List<String>> records = new ArrayList<>();
        List<String> record = new ArrayList<>();
        record.add("Name the lead singer of Queen?");
        record.add("James Hetfield");
        record.add("Freddie Mercury");
        records.add(record);
        QuestionReader questionReader = mock(QuestionsCsvReader.class);
        when(questionReader.getRecords()).thenReturn(records);
        QuestionDao questionDao = new QuestionDaoCsv(questionReader);
        IncorrectRecordFormatException thrown = assertThrows(IncorrectRecordFormatException.class, questionDao::getAllQuestions);
        assertEquals("The record does not contain the number of the answer to the question", thrown.getMessage());
    }
}
