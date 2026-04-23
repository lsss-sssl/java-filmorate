package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public final class Review {
    private long id;
    private String content;
    @JsonProperty("isPositive")
    private boolean positive;
    private long userId;
    private long filmId;
    private long useful;
}