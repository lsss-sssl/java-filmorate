package ru.yandex.practicum.filmorate.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public final class ReviewDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long reviewId;
    private String content;
    @JsonProperty("isPositive")
    private boolean positive;
    private long userId;
    private long filmId;
    private long useful;
}