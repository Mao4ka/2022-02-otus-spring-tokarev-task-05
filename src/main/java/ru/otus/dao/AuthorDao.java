package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author getById(long id);

    List<Author> getAll();

    void create(Author author);

    void update(Author author);

    long count();

    void deleteById(long id);

    void deleteAll();

}
