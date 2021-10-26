package ru.otus.spring.router;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.controllers.dto.BookDto;
import ru.otus.spring.controllers.mapper.DtoMapper;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SuppressWarnings("rawtypes")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WebRouterTest {
    private static final Author author1 = new Author("1", "Author1");
    private static final Author author2 = new Author("2", "Author2");
    private static final Genre genre1 = new Genre("1", "Genre1");
    private static final Genre genre2 = new Genre("2", "Genre2");

    private static final Book book1 = new Book("1", "Book1", author1, genre1);
    private static final Book book2 = new Book("2", "Book2", author2, genre2);

    @Autowired
    private RouterFunction routeBook;

    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private DtoMapper<Book, BookDto> mapper;

    @Captor
    private ArgumentCaptor<Book> captor;

    @BeforeEach
    public void preparation() {
        webTestClient = WebTestClient
                .bindToRouterFunction(routeBook)
                .build();
    }


    @Test
    public void shouldReturnBooksList() {
        given(bookRepository.findAll()).willReturn(Flux.just(book1, book2));

        webTestClient.get().uri("/api/books").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON).expectBodyList(BookDto.class)
                .contains(mapper.toDto(book1), mapper.toDto(book2));
        then(bookRepository).should().findAll();
    }

    @Test
    void shouldAddNewBook() {
        given(genreRepository.findById(genre1.getId())).willReturn(Mono.just(genre1));
        given(authorRepository.findById(author1.getId())).willReturn(Mono.just(author1));
        given(bookRepository.save(any())).willReturn(Mono.just(book1));

        webTestClient.post().uri("/api/books").accept(MediaType.APPLICATION_JSON).bodyValue(mapper.toDto(book1)).exchange()
                .expectStatus().isOk().expectBody(BookDto.class)
                .value(bookDto -> assertThat(bookDto.getId()).isNotNull().isNotBlank());

        then(genreRepository).should().findById(genre1.getId());
        then(authorRepository).should().findById(author1.getId());
        then(bookRepository).should().save(captor.capture());
        assertThat(captor.getValue().getGenre()).isEqualTo(genre1);
        assertThat(captor.getValue().getAuthor()).isEqualTo(author1);
    }

    @Test
    void shouldRemoveBook() {
        final String bookId = "987654321";
        given(bookRepository.deleteById(bookId)).willReturn(Mono.empty());

        webTestClient.delete().uri("/api/books/{id}", bookId).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();

        then(bookRepository).should().deleteById(bookId);
    }
}