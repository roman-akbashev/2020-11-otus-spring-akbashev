package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public void create(String name) {
        genreRepository.save(Genre.builder().name(name).build());
    }

    @Transactional
    @Override
    public void update(long id, String name) {
        Genre genre = getById(id);
        genre.setName(name);
        genreRepository.save(genre);
    }

    @Override
    public String readAll() {
        return convertToTable(genreRepository.findAll());
    }

    @Override
    public String readByName(String name) {
        return convertToTable(genreRepository.findByName(name));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Genre genre = getById(id);
        if (!genreRepository.checkRelatedBooksByGenre(genre)) {
            genreRepository.remove(genre);
        } else
            throw new EntityCanNotBeDeletedException("Genre with id " + id + " can not be deleted because there is a link with the book");
    }

    @Override
    public Genre getById(long id) {
        return genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Genre with id " + id + " not found!"));
    }

    @Override
    public String count() {
        return String.valueOf(genreRepository.count());
    }

    private String convertToTable(List<Genre> genres) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-15s%-25s%n";
        table.append(String.format(tableFormat, "ID", "GENRE NAME"));
        genres.forEach(genre -> table.append(String.format(tableFormat, genre.getId(), genre.getName())));
        return table.toString();
    }
}
