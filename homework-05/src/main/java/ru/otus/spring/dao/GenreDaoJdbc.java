package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void insert(Genre genre) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", genre.getName());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into genres (name) values (:name)", parameters, keyHolder);
    }

    @Override
    public void update(Genre genre) {
        jdbcOperations.update("update genres set name = :name where id = :id",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Optional<Genre> getById(long id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject("select id, name from genres where id = :id",
                    Map.of("id", id), new GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getByName(String name) {
        return jdbcOperations.query("select id, name from genres where name = :name",
                Map.of("name", name), new GenreMapper());
    }

    @Override
    public boolean checkRelatedBooksById(long id) {
        Boolean result = jdbcOperations.queryForObject("select exists(select 1 from books b inner join genres g on g.id = b.genre_id " +
                "where b.genre_id = :id)", Map.of("id", id), Boolean.class);
        return result == null ? false : result;
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query("select id, name from genres", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbcOperations.update("delete from genres where id = :id", Map.of("id", id));
    }

    @Override
    public long count() {
        Long result = jdbcOperations.getJdbcOperations().queryForObject("select count(g.id) from genres g", Long.class);
        return result == null ? 0 : result;
    }


    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return Genre.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name")).build();
        }
    }
}
