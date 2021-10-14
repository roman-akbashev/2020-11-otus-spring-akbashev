package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.EntityAlreadyExistsException;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Transactional
    @Override
    public Book create(String name, String authorId, String genreId) {
        checkBookByAllParams(name, authorId, genreId);
        Book book = Book.builder()
                .name(name)
                .author(authorService.getById(authorId))
                .genre(genreService.getById(genreId)).build();
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book update(String id, String name, String authorId, String genreId) {
        checkBookByAllParams(name, authorId, genreId);
        Book book = getById(id);
        book.setName(name);
        book.setAuthor(authorService.getById(authorId));
        book.setGenre(genreService.getById(genreId));
        return bookRepository.save(book);
    }

    @Override
    public Book getByAllParameters(String name, String authorId, String genreId) {
        return bookRepository.findByNameAndAuthor_IdAndGenre_Id(name, authorId, genreId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with name %s, with author id %s, " +
                        "with genre id %s not found!", name, authorId, genreId)));
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public String read(String id) {
        Book book = getById(id);
        return book.toString();
    }

    @Override
    public List<Book> getByName(String name) {
        return bookRepository.findByName(name);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        Book book = getById(id);
        bookRepository.deleteById(book.getId());
    }

    @Override
    public Book getById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id %s not found!", id)));
    }

    @Override
    public String count() {
        return String.valueOf(bookRepository.count());
    }

    private void checkBookByAllParams(String name, String authorId, String genreId) {
        Optional<Book> book = bookRepository.findByNameAndAuthor_IdAndGenre_Id(name, authorId, genreId);
        if (book.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Book with name %s, with author %s, " +
                    "with genre %s already exists!", name, book.get().getAuthor().getName(), book.get().getGenre().getName()));
        }
    }

}
