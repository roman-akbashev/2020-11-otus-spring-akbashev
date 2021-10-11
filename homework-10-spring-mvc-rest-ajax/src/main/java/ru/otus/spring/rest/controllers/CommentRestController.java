package ru.otus.spring.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.rest.dto.CommentDto;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    @GetMapping("/api/books/{bookId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable String bookId) {
        return new ResponseEntity<>(commentService.getAllByBookId(bookId).stream().map(CommentDto::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/api/books/{bookId}/comments/{commentId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable("commentId") String commentId, @PathVariable("bookId") String bookId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(CommentDto.toDto(commentService.update(commentId, commentDto.getCommentText())), HttpStatus.OK);
    }

    @DeleteMapping("/api/books/{bookId}/comments/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable("commentId") String commentId, @PathVariable("bookId") String bookId) {
        commentService.deleteById(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/api/books/{bookId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable String bookId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(CommentDto.toDto(commentService.create(commentDto.getCommentText(), bookId)), HttpStatus.CREATED);
    }
}
