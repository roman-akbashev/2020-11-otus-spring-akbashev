package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.BookService;

import java.util.List;

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
        return convertBookListToTable(bookService.readAll());
    }

    @ShellMethod(value = "ReadBook", key = {"rb", "read-book"})
    public String readBook(@ShellOption long bookId) {
        return bookService.read(bookId);
    }

    @ShellMethod(value = "ReadBookByName", key = {"rbn", "read-book-by-name"})
    public String readBookByName(@ShellOption String bookName) {
        return convertBookListToTable(bookService.readByName(bookName));
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "ReadBookComments", key = {"rbc", "read-book-comments"})
    public String readBookComments(@ShellOption long bookId) {
        return convertCommentListToTable(bookService.getAllCommentsById(bookId));
    }

    @ShellMethod(value = "DeleteBook", key = {"db", "delete-book"})
    public void deleteBook(@ShellOption long bookId) {
        bookService.deleteById(bookId);
    }

    @ShellMethod(value = "CountBooks", key = {"cbs", "count-books"})
    public String countBooks() {
        return bookService.count();
    }

    private String convertBookListToTable(List<Book> books) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-15s%-25s%-25s%-25s%n";
        table.append(String.format(tableFormat, "ID", "BOOK NAME", "BOOK AUTHOR", "BOOK GENRE"));
        books.forEach(book -> table.append(String.format(tableFormat, book.getId(), book.getName(),
                book.getAuthor().getName(), book.getGenre().getName())));
        return table.toString();
    }

    private String convertCommentListToTable(List<Comment> comments) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-15s%-25s%n";
        table.append(String.format(tableFormat, "ID", "COMMENT TEXT"));
        comments.forEach(comment -> table.append(String.format(tableFormat, comment.getId(), comment.getCommentText())));
        return table.toString();
    }
}
