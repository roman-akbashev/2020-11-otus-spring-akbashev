package ru.otus.spring.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.rest.dto.CommentDto;
import ru.otus.spring.service.CommentService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    @GetMapping("/api/books/{bookId}/comments")
    public List<CommentDto> getComments(@PathVariable String bookId) {
        return commentService.getAllByBookId(bookId).stream().map(CommentDto::toDto).collect(Collectors.toList());
    }

    @PutMapping("/api/books/{bookId}/comments/{commentId}")
    public Map<String, Boolean> editComment(@PathVariable("commentId") String commentId, @PathVariable("bookId") String bookId, @RequestBody CommentDto commentDto) {
        commentService.update(commentId, commentDto.getCommentText());
        return Collections.singletonMap("edited", Boolean.TRUE);
    }

    @DeleteMapping("/api/books/{bookId}/comments/{commentId}")
    public Map<String, Boolean> deleteComment(@PathVariable("commentId") String commentId, @PathVariable("bookId") String bookId) {
        commentService.deleteById(commentId);
        return Collections.singletonMap("deleted", Boolean.TRUE);
    }

    @PostMapping("/api/books/{bookId}/comments")
    public Map<String, Boolean> createComment(@PathVariable String bookId, @RequestBody CommentDto commentDto) {
        commentService.create(commentDto.getCommentText(), bookId);
        return Collections.singletonMap("created", Boolean.TRUE);
    }
}
