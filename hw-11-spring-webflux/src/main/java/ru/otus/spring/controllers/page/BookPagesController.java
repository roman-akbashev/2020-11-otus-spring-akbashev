package ru.otus.spring.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class BookPagesController {

    @GetMapping("/")
    public Mono<String> startPage() {
        return Mono.just("redirect:/books.html");
    }
}
