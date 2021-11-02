package ru.otus.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

import java.util.List;

@Controller
@AllArgsConstructor
public class BookController {
    private static final String URL_BOOKS = "/books";

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @GetMapping("/books")
    public String getBooks(Model model) {
        List<Book> books = bookService.readAll();
        model.addAttribute("books", books);
        return "listBooks";
    }

    @GetMapping("/books/{id}")
    public String getBook(@PathVariable("id") String bookId, Model model) {
        Book book = bookService.getById(bookId);
        List<Comment> comments = commentService.readAllByBookId(bookId);
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "showBook";
    }

    @GetMapping("/books/{id}/edit")
    public String editBook(@PathVariable("id") String bookId, Model model) {
        Book book = bookService.getById(bookId);
        List<Author> authors = authorService.readAll();
        List<Genre> genres = genreService.readAll();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "editBook";
    }

    @GetMapping("/books/add")
    public String addBook(Model model) {
        List<Author> authors = authorService.readAll();
        List<Genre> genres = genreService.readAll();
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("action", "add");
        return "editBook";
    }

    @PostMapping("/books/{id}/remove")
    public RedirectView deleteBook(@PathVariable("id") String bookId) {
        bookService.deleteById(bookId);
        return new RedirectView(URL_BOOKS);
    }

    @PostMapping("/books/create")
    public RedirectView saveBook(Book book) {
        bookService.create(book.getName(), book.getAuthor().getId(), book.getGenre().getId());
        return new RedirectView(URL_BOOKS);
    }

    @PostMapping("/books/update")
    public RedirectView updateBook(Book book) {
        bookService.update(book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId());
        return new RedirectView(URL_BOOKS);
    }

}
