package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.AbstractRepositoryTest;
import ru.otus.spring.domain.Comment;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

class CommentRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void insert() {
        Comment expectedComment = commentRepository.save(Comment.builder().commentText("book comment 4").build());
        Comment actualComment = commentRepository.findById(expectedComment.getId()).orElseGet(() -> fail("Comment not found!"));
        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    void update() {
        Comment expectedComment = Comment.builder().id("1").commentText("book comment").build();
        commentRepository.save(expectedComment);
        Comment actualComment = commentRepository.findById(expectedComment.getId()).orElseGet(() -> fail("Comment not found!"));
        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    void findById() {
        Comment expectedComment = Comment.builder().id("2").commentText("book comment 2").build();
        Comment actualComment = commentRepository.findById(expectedComment.getId()).orElseGet(() -> fail("Comment not found!"));
        assertThat(actualComment.getCommentText()).isEqualTo(expectedComment.getCommentText());
    }

    @Test
    void remove() {
        final String commentForDeleteId = "3";
        Comment comment = commentRepository.findById(commentForDeleteId).orElseGet(() -> fail("Comment not found!"));
        commentRepository.deleteById(comment.getId());
        assertFalse(commentRepository.findById(commentForDeleteId).isPresent());
    }

}