package ru.yandex.practicum.filmorate.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public final class ReviewDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String content;
    private boolean isPositive;
    private long userId;
    private long filmId;
    private long useful;
}
