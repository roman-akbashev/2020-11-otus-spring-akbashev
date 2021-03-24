package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.AuthorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public void create(String name) {
        authorRepository.save(Author.builder().name(name).build());
    }

    @Override
    public List<Author> readAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> readByName(String name) {
        return authorRepository.findByName(name);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        Author author = getById(id);
        authorRepository.deleteById(author.getId());
    }

    @Override
    public Author getById(String id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found!"));
    }

    @Override
    public String count() {
        return String.valueOf(authorRepository.count());
    }

}
