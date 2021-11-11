package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
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

    @Transactional
    @Override
    public void update(long id, String name) {
        Author author = getById(id);
        author.setName(name);
        authorRepository.save(author);
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
    public void deleteById(long id) {
        Author author = getById(id);
        if (!authorRepository.checkRelatedBookByAuthorId(author.getId())) {
            authorRepository.deleteById(author.getId());
        } else
            throw new EntityCanNotBeDeletedException("Author with id " + id + " can not be deleted because there is a link with the book");
    }

    @Override
    public Author getById(long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found!"));
    }

    @Override
    public String count() {
        return String.valueOf(authorRepository.count());
    }

}
