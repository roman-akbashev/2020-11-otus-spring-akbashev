package ru.otus.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.CommentService;

@Controller
@AllArgsConstructor
public class CommentController {
    private static final String URL_BOOK_COMMENTS = "/books/%s";

    private final CommentService commentService;

    @GetMapping("/books/{bookId}/comments/add")
    public String addComment(@PathVariable String bookId, Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("action", "add");
        model.addAttribute("bookId", bookId);
        return "editComment";
    }

    @GetMapping("/books/{bookId}/comments/{commentId}/edit")
    public String editComment(Model model, @PathVariable String bookId, @PathVariable String commentId) {
        Comment comment = commentService.getById(commentId);
        model.addAttribute("comment", comment);
        model.addAttribute("bookId", bookId);
        return "editComment";
    }

    @PostMapping("/books/{bookId}/comments/{commentId}/remove")
    public RedirectView deleteComment(@PathVariable String bookId, @PathVariable String commentId) {
        commentService.deleteById(commentId);
        return new RedirectView(String.format(URL_BOOK_COMMENTS, bookId));
    }

    @PostMapping("/books/{bookId}/comments/create")
    public RedirectView saveComment(Comment comment, @PathVariable String bookId) {
        commentService.create(comment.getCommentText(), bookId);
        return new RedirectView(String.format(URL_BOOK_COMMENTS, bookId));
    }

    @PostMapping("/books/{bookId}/comments/update")
    public RedirectView updateComment(Comment comment, @PathVariable String bookId) {
        commentService.update(comment.getId(), comment.getCommentText());
        return new RedirectView(String.format(URL_BOOK_COMMENTS, bookId));
    }
}
