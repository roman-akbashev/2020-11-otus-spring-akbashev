package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.EntityNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public void create(String name, long authorId, long genreId) {
        Book book = Book.builder()
                .name(name)
                .author(authorService.getById(authorId))
                .genre(genreService.getById(genreId)).build();
        bookDao.insert(book);
    }

    @Override
    public void update(long id, String name, long authorId, long genreId) {
        if (bookDao.getById(id).isPresent()) {
            Book book = Book.builder()
                    .id(id)
                    .name(name)
                    .author(authorService.getById(authorId))
                    .genre(genreService.getById(genreId)).build();
            bookDao.update(book);
        } else throw new EntityNotFoundException("Book with id " + id + " not found!");
    }

    @Override
    public String readAll() {
        return convertToTable(bookDao.getAll());
    }

    @Override
    public String read(long id) {
        Book book = bookDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found!"));
        return book.toString();
    }

    @Override
    public String readByName(String name) {
        return convertToTable(bookDao.getByName(name));
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public String count() {
        return String.valueOf(bookDao.count());
    }

    private String convertToTable(List<Book> books) {
        StringBuffer table = new StringBuffer();
        String tableFormat = "%-15s%-25s%-25s%-25s%n";
        table.append(String.format(tableFormat, "ID", "BOOK NAME", "BOOK AUTHOR", "BOOK GENRE"));
        books.forEach(book -> table.append(String.format(tableFormat, book.getId(), book.getName(),
                book.getAuthor().getName(), book.getGenre().getName())));
        return table.toString();
    }

}
