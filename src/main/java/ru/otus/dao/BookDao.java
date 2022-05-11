package ru.otus.dao;

import ru.otus.domain.Book;

import java.util.List;

public interface BookDao {

    Book getById(long id);

    List<Book> getAll();

    void create(Book book);

    void update(Book book);

    long count();

    void deleteById(long id);

    void deleteAll();

}
