package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.controller.TestPrinter;
import ru.otus.spring.controller.TestPrinterImpl;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestPrinter testPrinter = context.getBean(TestPrinterImpl.class);
        testPrinter.print();
    }

}
