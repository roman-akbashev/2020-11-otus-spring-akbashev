package ru.otus.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.model.entities.AuthorJpa;

public interface AuthorRepositoryJpa extends JpaRepository<AuthorJpa, Long> {

}