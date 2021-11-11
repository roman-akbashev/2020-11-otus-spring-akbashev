package ru.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.exceptions.IncorrectRecordFormatException;
import ru.otus.spring.util.QuestionReader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DisplayName("Тестирование QuestionDao, проверка генерируемого исключения")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
public class QuestionDaoCsvExceptionTest {

    @MockBean
    private QuestionReader questionReader;

    @Autowired
    private QuestionDao questionDao;

    @BeforeEach
    public void initQuestionReaderMock() {
        List<List<String>> records = new ArrayList<>();
        List<String> record = new ArrayList<>();
        record.add("Name the lead singer of Queen?");
        record.add("James Hetfield");
        record.add("Freddie Mercury");
        records.add(record);
        given(questionReader.getRecords()).willReturn(records);
    }

    @DisplayName("Проверим, что если запись возвращаемая ридером не содержит в конце номера корректного ответа сгенерируется исключение")
    @Test
    public void shouldBeThrownAnExceptionWhenTheReaderRecordWithoutCorrectAnswerNumber() {
        IncorrectRecordFormatException thrown = assertThrows(IncorrectRecordFormatException.class, questionDao::getAllQuestions);
        assertEquals("The record does not contain the number of the answer to the question", thrown.getMessage());
    }
}
