package ru.otus.spring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.controllers.handlers.AuthorHandler;
import ru.otus.spring.controllers.handlers.BookHandler;
import ru.otus.spring.controllers.handlers.CommentHandler;
import ru.otus.spring.controllers.handlers.GenreHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class WebRouter {

    @Bean
    public RouterFunction<ServerResponse> routeGenre(GenreHandler genreHandler) {
        return RouterFunctions
                .route(GET("/api/genres").and(accept(APPLICATION_JSON)), genreHandler::getAll)
                .andRoute(POST("/api/genres").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), genreHandler::create)
                .andRoute(PUT("/api/genres/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), genreHandler::edit)
                .andRoute(DELETE("/api/genres/{id}"), genreHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routeAuthor(AuthorHandler authorHandler) {
        return RouterFunctions
                .route(GET("/api/authors").and(accept(APPLICATION_JSON)), authorHandler::getAll)
                .andRoute(POST("/api/authors").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), authorHandler::create)
                .andRoute(PUT("/api/authors/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), authorHandler::edit)
                .andRoute(DELETE("/api/authors/{id}"), authorHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routeBook(BookHandler bookHandler) {
        return RouterFunctions
                .route(GET("/api/books").and(accept(APPLICATION_JSON)), bookHandler::getAll)
                .andRoute(POST("/api/books").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), bookHandler::create)
                .andRoute(PUT("/api/books/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), bookHandler::edit)
                .andRoute(DELETE("/api/books/{id}"), bookHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routeComment(CommentHandler commentHandler) {
        return RouterFunctions
                .route(GET("/api/books/{bookId}/comments").and(accept(APPLICATION_JSON)), commentHandler::getAll)
                .andRoute(POST("/api/books/{bookId}/comments").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), commentHandler::create)
                .andRoute(PUT("/api/books/{bookId}/comments/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), commentHandler::edit)
                .andRoute(DELETE("/api/books/{bookId}/comments/{id}"), commentHandler::delete);
    }

}
