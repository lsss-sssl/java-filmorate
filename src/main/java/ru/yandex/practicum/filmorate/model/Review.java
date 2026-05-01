package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public final class Review {
    private Long id;
    private String content;
    private boolean positive;
    private long userId;
    private long filmId;
    private long useful;
}