package ru.otus.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.model.entities.GenreJpa;

public interface GenreRepositoryJpa extends JpaRepository<GenreJpa, Long> {

}