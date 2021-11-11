package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void insert(Author author) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", author.getName());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into authors (name) values (:name)", parameters, keyHolder);
    }

    @Override
    public void update(Author author) {
        jdbcOperations.update("update authors set name = :name where id = :id",
                Map.of("name", author.getName(), "id", author.getId()));
    }

    @Override
    public Optional<Author> getById(long id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject("select id, name from authors where id = :id",
                    Map.of("id", id), new AuthorMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getByName(String name) {
        return jdbcOperations.query("select id, name from authors where name = :name",
                Map.of("name", name), new AuthorMapper());
    }

    @Override
    public boolean checkRelatedBooksById(long id) {
        Boolean result = jdbcOperations.queryForObject("select exists(select 1 from books b inner join authors a on a.id = b.author_id " +
                "where b.author_id = :id)", Map.of("id", id), Boolean.class);
        return result == null ? false : result;
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("select id, name from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbcOperations.update("delete from authors where id = :id", Map.of("id", id));
    }

    @Override
    public long count() {
        Long result = jdbcOperations.getJdbcOperations().queryForObject("select count(a.id) from authors a", Long.class);
        return result == null ? 0 : result;
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return Author.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name")).build();
        }
    }
}
