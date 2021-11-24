package ru.otus.spring.rest.mapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.rest.dto.CommentDto;

@RequiredArgsConstructor
@Component
public class CommentMapper implements DtoMapper<Comment, CommentDto> {
    private final ModelMapper modelMapper;

    @Override
    public Comment toEntity(@NonNull CommentDto dto) {
        return modelMapper.map(dto, Comment.class);
    }

    @Override
    public CommentDto toDto(@NonNull Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }
}
