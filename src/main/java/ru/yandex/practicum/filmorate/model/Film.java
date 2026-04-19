package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode
public final class Film {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Mpa mpa;
    private Set<Genre> genres;
    private Set<Director> directors;

    public static Film fromId(long id) {
        Film film = new Film();
        film.setId(id);
        return film;
    }
}