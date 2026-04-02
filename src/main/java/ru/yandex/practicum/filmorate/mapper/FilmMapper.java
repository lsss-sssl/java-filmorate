package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.dto.mpa.MpaDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Comparator;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FilmMapper {
    public static Film mapToFilm(NewFilmRequest request) {
        Film film = new Film();
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());
        film.setMpa(request.getMpa());
        film.setGenres(request.getGenres());
        return film;
    }

    public static FilmDto mapToFilmDto(Film film) {
        FilmDto dto = new FilmDto();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setDuration(film.getDuration());
        dto.setMpa(
                film.getMpa() != null
                        ? new MpaDto(film.getMpa().getId(), film.getMpa().getName())
                        : null
        );
        dto.setGenres(
                film.getGenres() != null
                        ? film.getGenres().stream()
                          .sorted(Comparator.comparingLong(Genre::getId))
                          .map(g -> new GenreDto(g.getId(), g.getName()))
                          .toList()
                        : List.of()
        );
        return dto;
    }

    public static void updateFilmFields(Film film, UpdateFilmRequest request) {
        if (request.hasName()) film.setName(request.getName());
        if (request.hasDescription()) film.setDescription(request.getDescription());
        if (request.hasReleaseDate()) film.setReleaseDate(request.getReleaseDate());
        if (request.hasDuration()) film.setDuration(request.getDuration());
        if (request.hasMpa()) film.setMpa(request.getMpa());
        if (request.hasGenres()) film.setGenres(request.getGenres());
    }
}