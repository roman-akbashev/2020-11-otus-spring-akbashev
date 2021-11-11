package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.exceptions.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public void create(String name) {
        genreDao.insert(Genre.builder().name(name).build());
    }

    @Override
    public void update(long id, String name) {
        if (genreDao.getById(id).isPresent()) {
            genreDao.update(Genre.builder().id(id).name(name).build());
        } else throw new EntityNotFoundException("Genre with id " + id + " not found!");
    }

    @Override
    public String readAll() {
        return convertToTable(genreDao.getAll());
    }

    @Override
    public String readByName(String name) {
        return convertToTable(genreDao.getByName(name));
    }

    @Override
    public void deleteById(long id) {
        if (!genreDao.checkRelatedBooksById(id)) {
            genreDao.deleteById(id);
        } else
            throw new EntityCanNotBeDeletedException("Genre with id " + id + " can not be deleted because there is a link with the book");
    }

    @Override
    public Genre getById(long id) {
        return genreDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Genre with id " + id + " not found!"));
    }

    @Override
    public String count() {
        return String.valueOf(genreDao.count());
    }

    private String convertToTable(List<Genre> genres) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-15s%-25s%n";
        table.append(String.format(tableFormat, "ID", "GENRE NAME"));
        genres.forEach(genre -> table.append(String.format(tableFormat, genre.getId(), genre.getName())));
        return table.toString();
    }
}
