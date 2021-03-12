package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.CommentService;

@ShellComponent
@AllArgsConstructor
public class CommentCommands {
    private final CommentService commentService;

    @ShellMethod(value = "CreateComment", key = {"cc", "create-comment"})
    public void createComment(@ShellOption String commentText, @ShellOption long bookId) {
        commentService.create(commentText, bookId);
    }

    @ShellMethod(value = "UpdateComment", key = {"uc", "update-comment"})
    public void updateComment(@ShellOption long commentId, @ShellOption String commentText) {
        commentService.update(commentId, commentText);
    }

    @ShellMethod(value = "DeleteComment", key = {"dc", "delete-comment"})
    public void deleteComment(@ShellOption long commentId) {
        commentService.deleteById(commentId);
    }

}
