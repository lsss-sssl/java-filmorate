package ru.yandex.practicum.filmorate.dto.director;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

@Data
public class NewDirectorRequest {
    @NotBlank(message = "имя не должно быть пустым")
    private String name;
}
