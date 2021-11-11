package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.configs.AppProperties;
import ru.otus.spring.configs.ResourcesProperties;
import ru.otus.spring.controller.StudentTestingImpl;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, ResourcesProperties.class})
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class);
        var studentTesting = context.getBean(StudentTestingImpl.class);
        studentTesting.startTesting();
    }
}
