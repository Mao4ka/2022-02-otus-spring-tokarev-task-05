package ru.otus.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int DEFAULT_RECORDS_COUNT = 1;
    private static final int EXISTING_ID = 0;
    private static final String EXISTING_BOOK_TITLE = "title_1";

    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("возвращать ожидаемое количество записей в БД")
    @Test
    void count() {
        int booksCount = bookDao.count();
        assertThat(booksCount).isEqualTo(DEFAULT_RECORDS_COUNT);
    }

    @DisplayName("удалять имеющуюся запись в БД по ID")
    @Test
    void deleteById() {
        bookDao.deleteById(EXISTING_ID);

        int booksCount = bookDao.count();
        assertThat(booksCount).isEqualTo(DEFAULT_RECORDS_COUNT - 1);
    }

    @DisplayName("полностью очищать таблицу")
    @Test
    void deleteAll() {
        bookDao.deleteAll();

        int booksCount = bookDao.count();
        assertThat(booksCount).isEqualTo(0);
    }

    @DisplayName("находить запись по ID")
    @Test
    void getById() {
        Book book = bookDao.getById(EXISTING_ID);

        assertThat(book.getId()).isEqualTo(EXISTING_ID);
        assertThat(book.getTitle()).isEqualTo(EXISTING_BOOK_TITLE);
    }

    @DisplayName("создавать новую запись")
    @Test
    void create() {
        bookDao.create(new Book(1, "title_2", 1, 2));

        int booksCount = bookDao.count();
        assertThat(booksCount).isEqualTo(DEFAULT_RECORDS_COUNT + 1);
    }

    @DisplayName("заменять старую запись на новую с тем же IF")
    @Test
    void update() {
        Book newCorrectBook = new Book(EXISTING_ID, "title_2", 2, 2);
        bookDao.update(newCorrectBook);

        Book updatedBook = bookDao.getById(EXISTING_ID);
        assertThat(updatedBook).isEqualTo(newCorrectBook);

    }

    @DisplayName("находить все записи в таблице")
    @Test
    void getAll() {
        List<Book> allBooks = bookDao.getAll();
        assertThat(allBooks.size()).isEqualTo(DEFAULT_RECORDS_COUNT);

        Book addingBook = new Book(1, "title_2", 1, 2);
        bookDao.create(addingBook);

        List<Book> newAllBooks = bookDao.getAll();
        assertThat(newAllBooks.size()).isEqualTo(DEFAULT_RECORDS_COUNT + 1);
        assertThat(newAllBooks).contains(addingBook);
    }


}