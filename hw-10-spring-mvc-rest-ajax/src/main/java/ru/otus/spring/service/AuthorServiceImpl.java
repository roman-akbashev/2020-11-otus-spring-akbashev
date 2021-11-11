package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.EntityAlreadyExistsException;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.AuthorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public Author create(String name) {
        checkAuthorByName(name);
        return authorRepository.save(Author.builder().name(name).build());
    }

    @Override
    public Author update(String id, String name) {
        checkAuthorByName(name);
        Author author = getById(id);
        author.setName(name);
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with name %s not found!", name)));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        Author author = getById(id);
        authorRepository.deleteById(author.getId());
    }

    @Override
    public Author getById(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with id %s not found!", id)));
    }

    @Override
    public String count() {
        return String.valueOf(authorRepository.count());
    }

    private void checkAuthorByName(String name) {
        if (authorRepository.findByName(name).isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Author with name %s already exists!", name));
        }
    }

}
