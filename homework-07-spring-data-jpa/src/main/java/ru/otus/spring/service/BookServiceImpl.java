package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repositories.BookRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Transactional
    @Override
    public void create(String name, long authorId, long genreId) {
        Book book = Book.builder()
                .name(name)
                .author(authorService.getById(authorId))
                .genre(genreService.getById(genreId)).build();
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void update(long id, String name, long authorId, long genreId) {
        Book book = getById(id);
        book.setName(name);
        book.setAuthor(authorService.getById(authorId));
        book.setGenre(genreService.getById(genreId));
        bookRepository.save(book);
    }

    @Override
    public List<Book> readAll() {
        return bookRepository.findAll();
    }

    @Override
    public String read(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found!"));
        return book.toString();
    }

    @Override
    public List<Book> readByName(String name) {
        return bookRepository.findByName(name);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Book book = getById(id);
        bookRepository.deleteById(book.getId());
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found!"));
    }

    @Override
    public List<Comment> getAllCommentsById(long id) {
        Book book = getById(id);
        return book.getComments();
    }

    @Override
    public String count() {
        return String.valueOf(bookRepository.count());
    }

}
