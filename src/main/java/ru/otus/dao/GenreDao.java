package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreDao extends Dao {

    Genre getById(int id);

    List<Genre> getAll();

    void create(Genre genre);

    void update(Genre genre);

}
