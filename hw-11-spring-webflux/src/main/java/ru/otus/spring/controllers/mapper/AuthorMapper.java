package ru.otus.spring.controllers.mapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.controllers.dto.AuthorDto;

@RequiredArgsConstructor
@Component
public class AuthorMapper implements DtoMapper<Author, AuthorDto> {
    private final ModelMapper modelMapper;

    @Override
    public Author toEntity(@NonNull AuthorDto dto) {
        return modelMapper.map(dto, Author.class);
    }

    @Override
    public AuthorDto toDto(@NonNull Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }
}
