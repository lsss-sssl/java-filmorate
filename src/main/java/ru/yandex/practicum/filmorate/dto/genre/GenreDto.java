package ru.yandex.practicum.filmorate.dto.genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class GenreDto {
    private long id;
    private String name;
}
