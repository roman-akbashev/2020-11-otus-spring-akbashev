package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.exceptions.EntityNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public void create(String name) {
        authorDao.insert(Author.builder().name(name).build());
    }

    @Override
    public void update(long id, String name) {
        if (authorDao.getById(id).isPresent()) {
            authorDao.update(Author.builder().id(id).name(name).build());
        } else throw new EntityNotFoundException("Author with id " + id + " not found!");
    }

    @Override
    public String readAll() {
        return convertToTable(authorDao.getAll());
    }

    @Override
    public String readByName(String name) {
        return convertToTable(authorDao.getByName(name));
    }

    @Override
    public void deleteById(long id) {
        if (!authorDao.checkRelatedBooksById(id)) {
            authorDao.deleteById(id);
        } else
            throw new EntityCanNotBeDeletedException("Author with id " + id + " can not be deleted because there is a link with the book");
    }

    @Override
    public Author getById(long id) {
        return authorDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found!"));
    }

    @Override
    public String count() {
        return String.valueOf(authorDao.count());
    }

    private String convertToTable(List<Author> authors) {
        String tableFormat = "%-15s%-25s%n";
        StringBuffer table = new StringBuffer();
        table.append(String.format(tableFormat, "ID", "AUTHOR NAME"));
        authors.forEach(author -> table.append(String.format(tableFormat, author.getId(), author.getName())));
        return table.toString();
    }
}
