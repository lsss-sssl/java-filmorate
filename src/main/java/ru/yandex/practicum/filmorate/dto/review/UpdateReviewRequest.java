package ru.yandex.practicum.filmorate.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class UpdateReviewRequest {
    @NotNull(message = "Id обязателен")
    private Long reviewId;
    @NotBlank(message = "Текст отзыва не может быть пустым")
    private String content;
    @NotNull(message = "Тип отзыва обязателен")
    @JsonProperty("isPositive")
    @NotNull
    private Boolean positive;
    @NotNull(message = "Id обязателен")
    private Long userId;
    @NotNull(message = "Id обязателен")
    private Long filmId;

    public boolean isPositive() {
        return positive;
    }
}