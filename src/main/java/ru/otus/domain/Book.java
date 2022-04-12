package ru.otus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Book {
    private final int id;
    private final String title;
    private final int genreId;
    private final int authorId;
}
