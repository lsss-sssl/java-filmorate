package ru.yandex.practicum.filmorate.dto.director;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private  long id;
    private String firstName;
    private String lastName;
    private Set<Film> films;
}
