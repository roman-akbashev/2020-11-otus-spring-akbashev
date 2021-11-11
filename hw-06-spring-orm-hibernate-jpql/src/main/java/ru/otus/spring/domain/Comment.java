package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text", nullable = false, unique = true)
    private String commentText;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
}
