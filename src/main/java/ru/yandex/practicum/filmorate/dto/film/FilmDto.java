package ru.yandex.practicum.filmorate.dto.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.dto.director.DirectorDto;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.dto.mpa.MpaDto;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public final class FilmDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private MpaDto mpa;
    private List<GenreDto> genres;
    private List<DirectorDto> directors;
}