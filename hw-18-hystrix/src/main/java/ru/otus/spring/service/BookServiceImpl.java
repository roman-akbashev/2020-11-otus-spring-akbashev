package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityAlreadyExistsException;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Transactional
    @HystrixCommand(fallbackMethod = "createBookFallbackMethod")
    @Override
    public Book create(String name, String authorId, String genreId) {
        checkBookByAllParams(name, authorId, genreId);
        Book book = Book.builder()
                .name(name)
                .author(authorService.getById(authorId))
                .genre(genreService.getById(genreId)).build();
        return bookRepository.save(book);
    }

    private Book createBookFallbackMethod(String name, String authorId, String genreId) {
        Author author = Author.builder().id(authorId).name("N/A").build();
        Genre genre = Genre.builder().id(genreId).name("N/A").build();
        return Book.builder().id("1").name(name).author(author).genre(genre).build();
    }

    @Transactional
    @HystrixCommand(fallbackMethod = "updateBookFallbackMethod")
    @Override
    public Book update(String id, String name, String authorId, String genreId) {
        checkBookByAllParams(name, authorId, genreId);
        Book book = getById(id);
        book.setName(name);
        book.setAuthor(authorService.getById(authorId));
        book.setGenre(genreService.getById(genreId));
        return bookRepository.save(book);
    }

    private Book updateBookFallbackMethod(String id, String name, String authorId, String genreId) {
        Author author = Author.builder().id(authorId).name("N/A").build();
        Genre genre = Genre.builder().id(genreId).name("N/A").build();
        return Book.builder().id(id).name(name).author(author).genre(genre).build();
    }

    @HystrixCommand(fallbackMethod = "getAllBooksFallbackMethod")
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    private List<Book> getAllBooksFallbackMethod() {
        List<Book> books = new ArrayList<>();
        Author author = Author.builder().id("1").name("N/A").build();
        Genre genre = Genre.builder().id("2").name("N/A").build();
        books.add(Book.builder().id("1").name("N/A").author(author).genre(genre).build());
        books.add(Book.builder().id("2").name("N/A").author(author).genre(genre).build());
        return books;
    }

    @Transactional
    @HystrixCommand(fallbackMethod = "deleteBookFallbackMethod")
    @Override
    public void deleteById(String id) {
        Book book = getById(id);
        bookRepository.deleteById(book.getId());
    }

    private void deleteBookFallbackMethod(String id) {
    }

    @HystrixCommand(fallbackMethod = "getBookFallbackMethod")
    @Override
    public Book getById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id %s not found!", id)));
    }

    private Book getBookFallbackMethod(String id) {
        Author author = Author.builder().id("1").name("N/A").build();
        Genre genre = Genre.builder().id("2").name("N/A").build();
        return Book.builder().id(id).name("N/A").author(author).genre(genre).build();
    }

    private void checkBookByAllParams(String name, String authorId, String genreId) {
        Optional<Book> book = bookRepository.findByNameAndAuthor_IdAndGenre_Id(name, authorId, genreId);
        if (book.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Book with name %s, with author %s, " +
                    "with genre %s already exists!", name, book.get().getAuthor().getName(), book.get().getGenre().getName()));
        }
    }

}
