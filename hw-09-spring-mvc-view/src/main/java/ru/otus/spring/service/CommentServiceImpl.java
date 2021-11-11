package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.CommentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Transactional
    @Override
    public void create(String commentText, String bookId) {
        Book book = bookService.getById(bookId);
        commentRepository.save(Comment.builder().commentText(commentText).book(book).build());
    }

    @Transactional
    @Override
    public void update(String id, String commentText) {
        Comment comment = getById(id);
        comment.setCommentText(commentText);
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        Comment comment = getById(id);
        commentRepository.deleteById(comment.getId());
    }

    @Override
    public Comment getById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found!"));
    }

    @Override
    public List<Comment> readAllByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    public void deleteAllByBookId(String bookId) {
        commentRepository.deleteAllByBookId(bookId);
    }
}
