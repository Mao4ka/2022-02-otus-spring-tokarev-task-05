package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDao extends Dao {

    Author getById(int id);

    List<Author> getAll();

    void create(Author author);

    void update(Author author);

}
