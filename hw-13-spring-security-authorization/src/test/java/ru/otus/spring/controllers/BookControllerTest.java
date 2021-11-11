package ru.otus.spring.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void shouldReturnOkForAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void getBooks() throws Exception {
        Author author = Author.builder().id("3").name("Author3").build();
        Genre genre = Genre.builder().id("3").name("Genre3").build();
        Book book = Book.builder().id("3").name("Book3").author(author).genre(genre).build();
        List<Book> books = List.of(book);
        when(bookService.readAll()).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("listBooks"))
                .andExpect(content().string(containsString(book.getName())));
        verify(bookService).readAll();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void getBook() throws Exception {
        final String bookId = "1";
        Author author = Author.builder().id("3").name("Author3").build();
        Genre genre = Genre.builder().id("3").name("Genre3").build();
        Book book = Book.builder().id(bookId).name("Book3").author(author).genre(genre).build();
        when(bookService.getById(bookId)).thenReturn(book);
        Comment comment = new Comment("1", "Comment", book);
        List<Comment> comments = List.of(comment);
        when(commentService.readAllByBookId(bookId)).thenReturn(comments);
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/books/%s", bookId)))
                .andExpect(status().isOk())
                .andExpect(view().name("showBook"))
                .andExpect(content().string(containsString(book.getName())))
                .andExpect(content().string(containsString(comment.getCommentText())));
        verify(bookService).getById(bookId);
        verify(commentService).readAllByBookId(bookId);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void editBook() throws Exception {
        Author author = Author.builder().id("3").name("Author3").build();
        Genre genre = Genre.builder().id("3").name("Genre3").build();
        Book book = Book.builder().id("1").name("Book3").author(author).genre(genre).build();
        List<Author> authors = List.of(author);
        List<Genre> genres = List.of(genre);
        when(bookService.getById(book.getId())).thenReturn(book);
        when(authorService.readAll()).thenReturn(authors);
        when(genreService.readAll()).thenReturn(genres);
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/books/%s/edit", book.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("editBook"))
                .andExpect(content().string(containsString(book.getName())))
                .andExpect(content().string(containsString(author.getName())))
                .andExpect(content().string(containsString(genre.getName())));
        verify(bookService).getById(book.getId());
        verify(authorService).readAll();
        verify(genreService).readAll();
    }
}