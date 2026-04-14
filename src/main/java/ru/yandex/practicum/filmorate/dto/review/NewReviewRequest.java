package ru.yandex.practicum.filmorate.dto.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class NewReviewRequest {
    @NotBlank(message = "Текст отзыва не может быть пустым")
    private String content;
    @NotNull(message = "Тип отзыва обязателен")
    private boolean isPositive;
    @NotNull(message = "Id обязателен")
    private long userId;
    @NotNull(message = "Id обязателен")
    private long filmId;
}
