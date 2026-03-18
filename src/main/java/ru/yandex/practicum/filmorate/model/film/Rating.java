package ru.yandex.practicum.filmorate.model.film;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Rating {
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
}
