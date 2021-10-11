package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityAlreadyExistsException;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public Genre create(String name) {
        checkGenreByName(name);
        return genreRepository.save(Genre.builder().name(name).build());
    }

    @Transactional
    @Override
    public Genre update(String id, String name) {
        checkGenreByName(name);
        Genre genre = getById(id);
        genre.setName(name);
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Genre with name %s not found!", name)));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        Genre genre = getById(id);
        genreRepository.deleteById(genre.getId());
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Genre with id %s not found!", id)));
    }

    @Override
    public String count() {
        return String.valueOf(genreRepository.count());
    }

    private void checkGenreByName(String name) {
        if (genreRepository.findByName(name).isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Genre with name %s already exists!", name));
        }
    }

}
