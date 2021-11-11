package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.BookService;

@ShellComponent
@AllArgsConstructor
public class BookCommands {
    private final BookService bookService;

    @ShellMethod(value = "CreateBook", key = {"cb", "create-book"})
    public void createBook(@ShellOption String bookName, @ShellOption long authorId, @ShellOption long genreId) {
        bookService.create(bookName, authorId, genreId);
    }

    @ShellMethod(value = "UpdateBook", key = {"ub", "update-book"})
    public void updateBook(@ShellOption long bookId, @ShellOption String bookName, @ShellOption long authorId,
                           @ShellOption long genreId) {
        bookService.update(bookId, bookName, authorId, genreId);
    }

    @ShellMethod(value = "ReadBooks", key = {"rbs", "read-books"})
    public String readBooks() {
        return bookService.readAll();
    }

    @ShellMethod(value = "ReadBook", key = {"rb", "read-book"})
    public String readBook(@ShellOption long bookId) {
        return bookService.read(bookId);
    }

    @ShellMethod(value = "ReadBookComments", key = {"rbc", "read-book-comments"})
    public String readBookComments(@ShellOption long bookId) {
        return bookService.getAllCommentsById(bookId);
    }

    @ShellMethod(value = "ReadBookByName", key = {"rbn", "read-book-by-name"})
    public String readBookByName(@ShellOption String bookName) {
        return bookService.readByName(bookName);
    }

    @ShellMethod(value = "DeleteBook", key = {"db", "delete-book"})
    public void deleteBook(@ShellOption long bookId) {
        bookService.deleteById(bookId);
    }

    @ShellMethod(value = "CountBooks", key = {"cbs", "count-books"})
    public String countBooks() {
        return bookService.count();
    }
}
