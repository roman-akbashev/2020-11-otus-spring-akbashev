package ru.otus.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.model.entities.BookJpa;

public interface BookRepositoryJpa extends JpaRepository<BookJpa, Long> {

}