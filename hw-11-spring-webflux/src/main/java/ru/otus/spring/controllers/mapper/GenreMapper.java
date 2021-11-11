package ru.otus.spring.controllers.mapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.controllers.dto.GenreDto;

@RequiredArgsConstructor
@Component
public class GenreMapper implements DtoMapper<Genre, GenreDto> {
    private final ModelMapper modelMapper;

    @Override
    public Genre toEntity(@NonNull GenreDto dto) {
        return modelMapper.map(dto, Genre.class);
    }

    @Override
    public GenreDto toDto(@NonNull Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }
}
