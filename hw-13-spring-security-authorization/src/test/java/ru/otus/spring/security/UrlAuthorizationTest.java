package ru.otus.spring.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlAuthorizationTest {
    private static final Author author = Author.builder().id("3").name("Author3").build();
    private static final Genre genre = Genre.builder().id("3").name("Genre3").build();
    private static final Book book = Book.builder().id("1").name("Book3").author(author).genre(genre).build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;


    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void shouldEnabledAccessToBookEditUriForAdmin() throws Exception {
        List<Author> authors = List.of(author);
        List<Genre> genres = List.of(genre);
        when(bookService.getById(book.getId())).thenReturn(book);
        when(authorService.readAll()).thenReturn(authors);
        when(genreService.readAll()).thenReturn(genres);
        this.mockMvc.perform(get(String.format("/books/%s/edit", book.getId()))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void shouldEnabledAccessToBookAddUriForAdmin() throws Exception {
        List<Author> authors = List.of(author);
        List<Genre> genres = List.of(genre);
        when(authorService.readAll()).thenReturn(authors);
        when(genreService.readAll()).thenReturn(genres);
        this.mockMvc.perform(get("/books/add")).andDo(print()).andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/books/1/edit", "/books/add", "/books/1/remove", "/books/create", "/books/update"})
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void shouldNotEnabledAccessToSomeUriForNotAdminUser(String uri) throws Exception {
        this.mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/books", "/books/1", "/genres", "/authors"})
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void shouldEnabledAccessToBooksAndCommentsUriForUser(String uri) throws Exception {
        when(bookService.getById(book.getId())).thenReturn(book);
        this.mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk());
    }
}
