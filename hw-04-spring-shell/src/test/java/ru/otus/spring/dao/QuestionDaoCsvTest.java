package ru.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.domain.Question;
import ru.otus.spring.util.QuestionReader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Тестирование QuestionDao")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
public class QuestionDaoCsvTest {
    private final String questionText = "Name the lead singer of Queen?";
    private final String firstAnswerText = "James Hetfield";
    private final String secondAnswerText = "Freddie Mercury";
    private final int correctAnswerNumber = 1;


    @MockBean
    private QuestionReader questionReader;

    @Autowired
    private QuestionDao questionDao;

    @BeforeEach
    public void initQuestionReaderMock() {
        List<List<String>> records = new ArrayList<>();
        List<String> record = new ArrayList<>();
        record.add(questionText);
        record.add(firstAnswerText);
        record.add(secondAnswerText);
        record.add(String.valueOf(correctAnswerNumber));
        records.add(record);
        given(questionReader.getRecords()).willReturn(records);
    }

    @DisplayName("Проверка корректно возвращаемых результатов для QuestionDao")
    @Test
    public void questionTest() {
        Question question = questionDao.getAllQuestions().get(0);
        assertEquals(questionText, question.getText());
        assertEquals(firstAnswerText, question.getAnswers().get(0));
        assertEquals(secondAnswerText, question.getAnswers().get(1));
        assertEquals(correctAnswerNumber, question.getCorrectAnswerNumber());
    }
}
