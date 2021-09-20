package ru.otus.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.Comment;


@Data
@AllArgsConstructor
public class CommentDto {
    private String id;
    private String commentText;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getCommentText());
    }
}
