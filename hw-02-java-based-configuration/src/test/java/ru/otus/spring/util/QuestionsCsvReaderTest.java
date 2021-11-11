package ru.otus.spring.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.exceptions.IncorrectRecordFormatException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование QuestionsCsvReader")
public class QuestionsCsvReaderTest {

    @DisplayName("Получим необходимое исключение при инициализации ридера пустым файлом ресурсов")
    @Test
    public void shouldBeThrownAnExceptionWhenTheCsvFileIsEmpty() {
        String resourceName = "questionsEmpty.csv";
        QuestionsCsvReader questionsCsvReader = new QuestionsCsvReader(resourceName);
        IncorrectRecordFormatException thrown = assertThrows(IncorrectRecordFormatException.class, questionsCsvReader::getRecords);
        assertEquals("Resource file " + resourceName + " contains no records", thrown.getMessage());
    }

    @DisplayName("Получим необходимое исключение если структура csv файла не соответствует заявленным требованиям")
    @Test
    public void shouldBeThrownAnExceptionWhenTheCsvFileWithIncorrectStructure() {
        QuestionsCsvReader questionsCsvReader = new QuestionsCsvReader("questionsIncorrectStructure.csv");
        IncorrectRecordFormatException thrown = assertThrows(IncorrectRecordFormatException.class, questionsCsvReader::getRecords);
        assertEquals("The record must contain a question, two answers, and the number of the correct question. " +
                "Elements must be separated by a symbol \";\"", thrown.getMessage());
    }

    @DisplayName("Получим необходимое исключение если каждая строка csv файла не содержит номер правильного ответа")
    @Test
    public void shouldBeThrownAnExceptionWhenTheCsvFileWithoutCorrectAnswerNumber() {
        QuestionsCsvReader questionsCsvReader = new QuestionsCsvReader("questionsWithoutCorrectAnswerNumber.csv");
        IncorrectRecordFormatException thrown = assertThrows(IncorrectRecordFormatException.class, questionsCsvReader::getRecords);
        assertEquals("The record does not contain the number of the answer to the question", thrown.getMessage());
    }

    @DisplayName("Проверяем что корректный csv файл c большим количеством вопросов и ответов не сгенерирует никаких исключений")
    @Test
    public void shouldBeWithoutExceptionIfTheCsvFileIsCorrect() {
        QuestionsCsvReader questionsCsvReader = new QuestionsCsvReader("questionsWithAnyNumberOfQuestionsAndAnswers.csv");
        assertDoesNotThrow(questionsCsvReader::getRecords);
    }
}
