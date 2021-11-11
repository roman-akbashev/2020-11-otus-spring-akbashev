package ru.otus.spring.controllers.mapper;

public interface DtoMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);
}
