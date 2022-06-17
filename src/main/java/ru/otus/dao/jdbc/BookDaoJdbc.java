package ru.otus.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.BookDao;
import ru.otus.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations;
    }

    @Override
    public long count() {
        Integer cnt = jdbc.getJdbcOperations().queryForObject("select count(*) from Book", Integer.class);
        return cnt == null ? 0 : cnt;
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from Book where id = :id", Map.of("id", id));
    }

    @Override
    public void deleteAll() {
        jdbc.getJdbcOperations().execute("delete from Book");
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("select id, title, genre_id, author_id from Book where id = :id",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select id, title, genre_id, author_id from Book", new BookMapper());
    }

    @Override
    public void create(Book book) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("genreId", book.getGenreId());
        params.put("authorId", book.getAuthorId());

        jdbc.update("insert into Book (id, title, genre_id, author_id) " +
                "values (:id, :title, :genreId, :authorId)", params);
    }

    @Override
    public void update(Book book) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("genreId", book.getGenreId());
        params.put("authorId", book.getAuthorId());

        jdbc.update("update Book set title = :title, " +
                "genre_id = :genreId, " +
                "author_id = :authorId " +
                "where id = :id", params);
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            long genreId = rs.getLong("genre_id");
            long authorId = rs.getLong("author_id");

            return new Book(id, title, genreId, authorId);
        }
    }

}
