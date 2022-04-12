package ru.otus.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer cnt = jdbc.getJdbcOperations().queryForObject("select count(*) from Genre", Integer.class);
        return cnt == null ? 0 : cnt;
    }

    @Override
    public void deleteById(int id) {
        jdbc.update("delete from Genre where id = :id", Map.of("id", id));
    }

    @Override
    public void deleteAll() {
        jdbc.getJdbcOperations().execute("delete from Genre");
    }

    @Override
    public Genre getById(int id) {
        return jdbc.queryForObject("select id, genre_name from Genre where id = :id",
                Map.of("id", id), new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, genre_name from Genre", new GenreMapper());
    }

    @Override
    public void create(Genre genre) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", genre.getId());
        params.put("genreName", genre.getGenreName());

        jdbc.update("insert into Author (id, genre_name) values (:id, :genreName)", params);
    }

    @Override
    public void update(Genre genre) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", genre.getId());
        params.put("genreName", genre.getGenreName());

        jdbc.update("update Genre set genre_name = :genreName where id = :id", params);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String genreName = rs.getString("genre_name");
            return new Genre(id, genreName);
        }
    }

}
