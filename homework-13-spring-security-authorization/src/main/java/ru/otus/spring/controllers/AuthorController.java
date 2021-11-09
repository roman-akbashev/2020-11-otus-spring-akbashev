package ru.otus.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.AuthorService;

import java.util.List;

@Controller
@AllArgsConstructor
public class AuthorController {
    private static final String URL_AUTHORS = "/authors";
    private final AuthorService authorService;

    @GetMapping("/authors")
    public String getAuthors(Model model) {
        List<Author> authors = authorService.readAll();
        model.addAttribute("authors", authors);
        return "listAuthors";
    }

    @GetMapping("/authors/{id}/edit")
    public String editAuthor(@PathVariable("id") String authorId, Model model) {
        Author author = authorService.getById(authorId);
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @GetMapping("/authors/add")
    public String addAuthor(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("action", "add");
        return "editAuthor";
    }

    @PostMapping("/authors/{id}/remove")
    public RedirectView deleteAuthor(@PathVariable("id") String authorId) {
        authorService.deleteById(authorId);
        return new RedirectView(URL_AUTHORS);
    }

    @PostMapping("/authors/create")
    public RedirectView saveAuthor(Author author) {
        authorService.create(author.getName());
        return new RedirectView(URL_AUTHORS);
    }

    @PostMapping("/authors/update")
    public RedirectView updateAuthor(Author author) {
        authorService.update(author.getId(), author.getName());
        return new RedirectView(URL_AUTHORS);
    }
}
