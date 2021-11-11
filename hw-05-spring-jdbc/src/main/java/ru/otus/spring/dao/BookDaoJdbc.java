package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void insert(Book book) {
        Author author = book.getAuthor();
        Genre genre = book.getGenre();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", book.getName());
        parameters.addValue("author_id", author.getId());
        parameters.addValue("genre_id", genre.getId());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into books (name, author_id, genre_id) values (:name, :author_id, :genre_id)", parameters, keyHolder);
    }

    @Override
    public void update(Book book) {
        Author author = book.getAuthor();
        Genre genre = book.getGenre();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", book.getId());
        parameters.put("name", book.getName());
        parameters.put("author_id", author.getId());
        parameters.put("genre_id", genre.getId());
        jdbcOperations.update("update books set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id", parameters);
    }

    @Override
    public Optional<Book> getById(long id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject("select b.id id, b.name name, a.id author_id, " +
                            "a.name author_name, g.id genre_id, g.name genre_name from books b " +
                            "left join authors a on a.id = b.author_id left join genres g on g.id = b.genre_id where b.id = :id",
                    Map.of("id", id), new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getByName(String name) {
        return jdbcOperations.query("select b.id id, b.name name, a.id author_id, a.name author_name, g.id genre_id, " +
                "g.name genre_name from books b left join authors a on a.id = b.author_id left join genres g on g.id = b.genre_id " +
                "where b.name = :name", Map.of("name", name), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query("select b.id id, b.name name, a.id author_id, a.name author_name, g.id genre_id, g.name genre_name from books b " +
                "left join authors a on a.id = b.author_id " +
                "left join genres g on g.id = b.genre_id ", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbcOperations.update("delete from books where id = :id", Map.of("id", id));
    }

    @Override
    public long count() {
        Long result = jdbcOperations.getJdbcOperations().queryForObject("select count(b.id) from books b", Long.class);
        return result == null ? 0 : result;
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Author author = Author.builder()
                    .id(resultSet.getLong("author_id"))
                    .name(resultSet.getString("author_name")).build();

            Genre genre = Genre.builder()
                    .id(resultSet.getLong("genre_id"))
                    .name(resultSet.getString("genre_name")).build();

            return Book.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .author(author)
                    .genre(genre).build();
        }
    }
}
