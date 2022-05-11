package ru.otus.dao;

public interface Dao {

    long count();

    void deleteById(long id);

    void deleteAll();

}
