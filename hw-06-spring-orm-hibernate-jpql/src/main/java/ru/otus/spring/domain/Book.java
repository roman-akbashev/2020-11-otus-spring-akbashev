package ru.otus.spring.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "author-and-genre-entity-graph",
        attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = Author.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(targetEntity = Genre.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "book", targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Comment> comments;
}
