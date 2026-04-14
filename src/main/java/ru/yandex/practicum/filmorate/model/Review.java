package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public final class Review {
    private long id;
    private String content;
    private boolean isPositive;
    private long userId;
    private long filmId;
    private long useful;
}
