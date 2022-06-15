package ru.otus.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations;
    }

    @Override
    public long count() {
        //Integer cnt = jdbc.getJdbcOperations().queryForObject("select count(*) from Author", Integer.class);
        //return cnt == null ? 0 : cnt;
        return jdbc.getJdbcOperations().queryForObject("select count(*) from Author", Long.class);
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from Author where id = :id", Map.of("id", id));
    }

    @Override
    public void deleteAll() {
        jdbc.update("delete from Author", new HashMap<>());
        //jdbc.update("delete from Author", Collections.singletonMap()); -- не взлетает
    }


    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("select id, author_name from Author where id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, author_name from Author", new AuthorMapper());
    }

    @Override
    public void create(Author author) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", author.getId());
        params.put("authorName", author.getAuthorName());

        jdbc.update("insert into Author (id, author_name) values (:id, :authorName)", params);
    }

    @Override
    public void update(Author author) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", author.getId());
        params.put("authorName", author.getAuthorName());

        jdbc.update("update Author set author_name = :authorName where id = :id", params);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String authorName = rs.getString("author_name");
            return new Author(id, authorName);
        }
    }

}
