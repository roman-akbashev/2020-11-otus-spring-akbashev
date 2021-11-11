package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.CommentRepository;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Transactional
    @Override
    public void create(String commentText, long bookId) {
        Book book = bookService.getById(bookId);
        commentRepository.save(Comment.builder().commentText(commentText).book(book).build());
    }

    @Transactional
    @Override
    public void update(long id, String commentText) {
        Comment comment = getById(id);
        comment.setCommentText(commentText);
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Comment comment = getById(id);
        commentRepository.remove(comment);
    }

    @Override
    public Comment getById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found!"));
    }
}
