package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {
    private final PrintStream printStream;
    private final Scanner scanner;

    public ConsoleIOService(@Value("#{ T(java.lang.System).in}") InputStream inputStream,
                            @Value("#{ T(java.lang.System).out}") PrintStream printStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void print(String message) {
        printStream.println(message);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
