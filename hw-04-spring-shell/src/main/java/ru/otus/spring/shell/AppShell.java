package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.controller.StudentTesting;

@ShellComponent
@RequiredArgsConstructor
public class AppShell {
    private final StudentTesting studentTesting;

    @ShellMethod(value = "Start testing", key = {"s", "start"})
    public void startTesting() {
        studentTesting.startTesting();
    }
}
