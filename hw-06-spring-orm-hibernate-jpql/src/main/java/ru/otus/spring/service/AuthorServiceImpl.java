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
    public String readAll() {
        return convertToTable(authorRepository.findAll());
    }

    @Override
    public String readByName(String name) {
        return convertToTable(authorRepository.findByName(name));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Author author = getById(id);
        if (!authorRepository.checkRelatedBookByAuthor(author)) {
            authorRepository.remove(author);
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

    private String convertToTable(List<Author> authors) {
        String tableFormat = "%-15s%-25s%n";
        StringBuffer table = new StringBuffer();
        table.append(String.format(tableFormat, "ID", "AUTHOR NAME"));
        authors.forEach(author -> table.append(String.format(tableFormat, author.getId(), author.getName())));
        return table.toString();
    }
}
