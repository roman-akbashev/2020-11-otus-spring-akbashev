package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repositories.CommentRepository;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommentServiceImpl.class)
class CommentServiceImplTest {
    private static final long COMMENT_ID = 1L;
    private static final String COMMENT_TEXT = "Comment1";
    private static final long BOOK_ID = 1L;
    private static final String BOOK_NAME = "Book1";

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookService bookService;

    @Test
    void create() {
        Book book = Book.builder().id(BOOK_ID).name(BOOK_NAME).build();
        Mockito.when(bookService.getById(BOOK_ID)).thenReturn(book);
        Comment comment = Comment.builder().commentText(COMMENT_TEXT).book(book).build();
        commentService.create(COMMENT_TEXT, book.getId());
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void update() {
        Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.ofNullable(Comment.builder().id(COMMENT_ID).build()));
        Comment comment = Comment.builder().id(COMMENT_ID).commentText(COMMENT_TEXT).build();
        commentService.update(COMMENT_ID, COMMENT_TEXT);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void deleteById() {
        Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.ofNullable(Comment.builder().id(COMMENT_ID).build()));
        commentService.deleteById(COMMENT_ID);
        verify(commentRepository, times(1)).deleteById(COMMENT_ID);
    }

    @Test
    void getById() {
        Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.ofNullable(Comment.builder().id(COMMENT_ID).build()));
        commentService.getById(COMMENT_ID);
        verify(commentRepository, times(1)).findById(COMMENT_ID);
    }
}