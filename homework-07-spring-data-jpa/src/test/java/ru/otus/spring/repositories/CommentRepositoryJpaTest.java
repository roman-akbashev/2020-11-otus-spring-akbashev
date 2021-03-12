package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.domain.Comment;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
class CommentRepositoryJpaTest {

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
        Comment expectedComment = Comment.builder().id(1L).commentText("book comment").build();
        commentRepository.save(expectedComment);
        Comment actualComment = commentRepository.findById(expectedComment.getId()).orElseGet(() -> fail("Comment not found!"));
        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    void findById() {
        Comment expectedComment = Comment.builder().id(2L).commentText("book comment 2").build();
        Comment actualComment = commentRepository.findById(expectedComment.getId()).orElseGet(() -> fail("Comment not found!"));
        assertThat(actualComment.getCommentText()).isEqualTo(expectedComment.getCommentText());
    }

    @Test
    void remove() {
        final long commentForDeleteId = 3L;
        Comment comment = commentRepository.findById(commentForDeleteId).orElseGet(() -> fail("Comment not found!"));
        commentRepository.deleteById(comment.getId());
        assertFalse(commentRepository.findById(commentForDeleteId).isPresent());
    }

}