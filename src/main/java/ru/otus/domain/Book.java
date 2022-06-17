package ru.otus.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Book {
    private final long id;
    private final String title;
    private final long genreId;
    private final long authorId;
}
