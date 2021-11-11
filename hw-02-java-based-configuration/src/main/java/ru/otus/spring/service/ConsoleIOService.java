package ru.otus.spring.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleIOService implements IOService {
    private final PrintStream printStream;
    private final Scanner scanner;

    public ConsoleIOService(InputStream inputStream, PrintStream printStream) {
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
