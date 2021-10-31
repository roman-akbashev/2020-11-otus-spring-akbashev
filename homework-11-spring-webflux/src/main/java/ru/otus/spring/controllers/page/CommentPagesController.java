package ru.otus.spring.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentPagesController {
    @GetMapping("/comments")
    public String getAll(@RequestParam("bookId") String bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "comments";
    }
}
