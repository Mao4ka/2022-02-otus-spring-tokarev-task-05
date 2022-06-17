package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreDao {

    Genre getById(long id);

    List<Genre> getAll();

    void create(Genre genre);

    void update(Genre genre);

    long count();

    void deleteById(long id);

    void deleteAll();

}
