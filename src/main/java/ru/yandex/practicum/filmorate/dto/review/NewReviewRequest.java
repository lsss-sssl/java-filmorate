package ru.yandex.practicum.filmorate.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class NewReviewRequest {
    @NotBlank(message = "Текст отзыва не может быть пустым")
    private String content;
    @NotNull(message = "Тип отзыва обязателен")
    @JsonProperty("isPositive")
    private Boolean positive;
    @NotNull(message = "Id обязателен")
    private Long userId;
    @NotNull(message = "Id обязателен")
    private Long filmId;

    public boolean isPositive() {
        return positive;
    }
}