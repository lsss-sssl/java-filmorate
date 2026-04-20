package ru.yandex.practicum.filmorate.dto.director;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewDirectorRequest {
    @NotBlank(message = "имя не должно быть пустым")
    private String name;
}
