package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.controller.StudentTesting;
import ru.otus.spring.controller.StudentTestingImpl;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StudentTesting studentTesting = context.getBean(StudentTestingImpl.class);
        studentTesting.startTesting();
    }
}
