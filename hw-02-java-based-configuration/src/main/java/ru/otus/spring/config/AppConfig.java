package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.service.ConsoleIOService;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionServiceImpl;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
@ComponentScan(basePackages = "ru.otus.spring")
public class AppConfig {

    @Bean
    IOService ioService(@Value("#{ T(java.lang.System).in}") InputStream inputStream,
                        @Value("#{ T(java.lang.System).out}") PrintStream printStream) {
        return new ConsoleIOService(inputStream, printStream);
    }

    @Bean
    QuestionService questionService(QuestionDao questionDao) {
        return new QuestionServiceImpl(questionDao);
    }
}
