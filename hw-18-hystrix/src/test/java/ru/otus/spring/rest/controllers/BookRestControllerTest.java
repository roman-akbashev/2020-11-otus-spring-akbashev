package ru.otus.spring.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.EntityAlreadyExistsException;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.rest.dto.ApiError;
import ru.otus.spring.rest.dto.BookDto;
import ru.otus.spring.rest.mapper.DtoMapper;
import ru.otus.spring.service.BookService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Rest-контроллер по книгам должен ")
@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerTest {
    private static final Author author1 = new Author("1", "Author1");
    private static final Author author2 = new Author("2", "Author2");
    private static final Genre genre1 = new Genre("1", "Genre1");
    private static final Genre genre2 = new Genre("2", "Genre2");

    private static final Book book1 = new Book("1", "Book1", author1, genre1);
    private static final Book book2 = new Book("2", "Book2", author2, genre2);

    @Autowired
    private DtoMapper<Book, BookDto> mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    private static MockHttpServletRequestBuilder putJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return put(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("успешно возвращать список книг")
    @Test
    void getBooks() throws Exception {
        var books = List.of(book1, book2);
        var booksDto = List.of(mapper.toDto(book1), mapper.toDto(book2));
        given(bookService.getAll()).willReturn(books);
        mvc
                .perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(booksDto))))
        ;
    }

    @DisplayName("возвращать ошибку при получении книги которой нет")
    @Test
    void getBookWithNotFoundException() throws Exception {
        String errorMessage = String.format("Book with id %s not found!", book1.getId());
        given(bookService.getById(book1.getId())).willThrow(new EntityNotFoundException(errorMessage));
        ApiError apiError = new ApiError(errorMessage);
        mvc
                .perform(get("/api/books/" + book1.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(apiError))))
        ;
    }

    @DisplayName("успешно возвращать книгу")
    @Test
    void getBook() throws Exception {
        given(bookService.getById(book1.getId())).willReturn(book1);
        mvc
                .perform(get("/api/books/" + book1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(mapper.toDto(book1)))))
        ;
    }

    @DisplayName("успешно редактировать книгу")
    @Test
    void editBook() throws Exception {
        given(bookService.update(book1.getId(), book1.getName(), author1.getId(), genre1.getId())).willReturn(book1);
        mvc
                .perform(putJson("/api/books/" + book1.getId(), mapper.toDto(book1)))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(mapper.toDto(book1)))))
        ;
    }

    @DisplayName("возвращать ошибку при редактировании книги которой нет")
    @Test
    void editBookWithNotFoundException() throws Exception {
        String errorMessage = String.format("Book with id %s not found!", book1.getId());
        given(bookService.update(book1.getId(), book1.getName(), author1.getId(), genre1.getId())).willThrow(new EntityNotFoundException(errorMessage));
        ApiError apiError = new ApiError(errorMessage);
        mvc
                .perform(putJson("/api/books/" + book1.getId(), mapper.toDto(book1)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(apiError))))
        ;
    }

    @DisplayName("успешно удалять книгу")
    @Test
    void deleteBook() throws Exception {
        mvc
                .perform(delete("/api/books/" + book1.getId()))
                .andExpect(status().isNoContent())
        ;
    }

    @DisplayName("возвращать ошибку при удалении не существующей книги")
    @Test
    void deleteBookWithNotFoundException() throws Exception {
        String errorMessage = String.format("Book with id %s not found!", book1.getId());
        doThrow(new EntityNotFoundException(errorMessage)).when(bookService).deleteById(book1.getId());
        ApiError apiError = new ApiError(errorMessage);
        mvc
                .perform(delete("/api/books/" + book1.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(apiError))))
        ;
    }

    @DisplayName("возвращать ошибку при редактировании книги с существующими параметрами")
    @Test
    void editBookWithAlreadyExistsException() throws Exception {
        String errorMessage = String.format("Book with name %s, with author %s, " +
                "with genre %s already exists!", book1.getName(), author1.getName(), genre1.getName());
        given(bookService.update(book1.getId(), book1.getName(), author1.getId(), genre1.getId())).willThrow(new EntityAlreadyExistsException(errorMessage));
        ApiError apiError = new ApiError(errorMessage);
        mvc
                .perform(putJson("/api/books/" + book1.getId(), mapper.toDto(book1)))
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(apiError))))
        ;
    }

    @DisplayName("успешно создавать книгу")
    @Test
    void createBook() throws Exception {
        given(bookService.create(book1.getName(), author1.getId(), genre1.getId())).willReturn(book1);
        mvc
                .perform(post("/api/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mapper.toDto(book1))))
                .andExpect(status().isCreated())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(mapper.toDto(book1)))))
        ;
    }

    @DisplayName("возвращать ошибку при создании книги с существующими параметрами")
    @Test
    void createBookWithAlreadyExistsException() throws Exception {
        String errorMessage = String.format("Book with name %s, with author %s, " +
                "with genre %s already exists!", book1.getName(), author1.getName(), genre1.getName());
        given(bookService.create(book1.getName(), author1.getId(), genre1.getId())).willThrow(new EntityAlreadyExistsException(errorMessage));
        ApiError apiError = new ApiError(errorMessage);
        mvc
                .perform(post("/api/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mapper.toDto(book1))))
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(apiError))))
        ;
    }
}