package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class BookCommands {
    private final BookService bookService;
    private final CommentService commentService;

    @ShellMethod(value = "CreateBook", key = {"cb", "create-book"})
    public void createBook(@ShellOption String bookName, @ShellOption String authorId, @ShellOption String genreId) {
        bookService.create(bookName, authorId, genreId);
    }

    @ShellMethod(value = "UpdateBook", key = {"ub", "update-book"})
    public void updateBook(@ShellOption String bookId, @ShellOption String bookName, @ShellOption String authorId,
                           @ShellOption String genreId) {
        bookService.update(bookId, bookName, authorId, genreId);
    }

    @ShellMethod(value = "ReadBooks", key = {"rbs", "read-books"})
    public String readBooks() {
        return convertBookListToTable(bookService.readAll());
    }

    @ShellMethod(value = "ReadBook", key = {"rb", "read-book"})
    public String readBook(@ShellOption String bookId) {
        return bookService.read(bookId);
    }

    @ShellMethod(value = "ReadBookByName", key = {"rbn", "read-book-by-name"})
    public String readBookByName(@ShellOption String bookName) {
        return convertBookListToTable(bookService.readByName(bookName));
    }

    @ShellMethod(value = "ReadBookComments", key = {"rbc", "read-book-comments"})
    public String readBookComments(@ShellOption String bookId) {
        return convertCommentListToTable(commentService.readAllByBookId(bookId));
    }

    @ShellMethod(value = "DeleteBook", key = {"db", "delete-book"})
    public void deleteBook(@ShellOption String bookId) {
        bookService.deleteById(bookId);
    }

    @ShellMethod(value = "CountBooks", key = {"cbs", "count-books"})
    public String countBooks() {
        return bookService.count();
    }

    private String convertBookListToTable(List<Book> books) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-35s%-25s%-25s%-25s%n";
        table.append(String.format(tableFormat, "ID", "BOOK NAME", "BOOK AUTHOR", "BOOK GENRE"));
        books.forEach(book -> table.append(String.format(tableFormat, book.getId(), book.getName(),
                book.getAuthor().getName(), book.getGenre().getName())));
        return table.toString();
    }

    private String convertCommentListToTable(List<Comment> comments) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-35s%-25s%n";
        table.append(String.format(tableFormat, "ID", "COMMENT TEXT"));
        comments.forEach(comment -> table.append(String.format(tableFormat, comment.getId(), comment.getCommentText())));
        return table.toString();
    }
}
