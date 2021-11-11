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
    public String readAll() {
        return convertBookListToTable(bookRepository.findAll());
    }

    @Override
    public String read(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found!"));
        return book.toString();
    }

    @Override
    public String readByName(String name) {
        return convertBookListToTable(bookRepository.findByName(name));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Book book = getById(id);
        bookRepository.remove(book);
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found!"));
    }

    @Transactional(readOnly = true)
    @Override
    public String getAllCommentsById(long id) {
        Book book = getById(id);
        return convertCommentListToTable(book.getComments());
    }

    @Override
    public String count() {
        return String.valueOf(bookRepository.count());
    }

    private String convertBookListToTable(List<Book> books) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-15s%-25s%-25s%-25s%n";
        table.append(String.format(tableFormat, "ID", "BOOK NAME", "BOOK AUTHOR", "BOOK GENRE"));
        books.forEach(book -> table.append(String.format(tableFormat, book.getId(), book.getName(),
                book.getAuthor().getName(), book.getGenre().getName())));
        return table.toString();
    }

    private String convertCommentListToTable(List<Comment> comments) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-15s%-25s%n";
        table.append(String.format(tableFormat, "ID", "COMMENT TEXT"));
        comments.forEach(comment -> table.append(String.format(tableFormat, comment.getId(), comment.getCommentText())));
        return table.toString();
    }

}
