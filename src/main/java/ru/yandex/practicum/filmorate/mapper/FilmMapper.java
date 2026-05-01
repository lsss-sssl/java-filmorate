package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.director.DirectorDto;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.dto.mpa.MpaDto;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Comparator;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FilmMapper {
    public static Film mapToFilm(NewFilmRequest request) {
        return Film.builder()
                .name(request.getName())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .duration(request.getDuration())
                .mpa(request.getMpa())
                .genres(request.getGenres())
                .directors(request.getDirectors())
                .build();
    }

    public static FilmDto mapToFilmDto(Film film) {
        return FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .mpa(
                        film.getMpa() != null
                                ? MpaDto.builder()
                                  .id(film.getMpa().getId())
                                  .name(film.getMpa().getName())
                                  .build()
                                : null
                )
                .genres(
                        film.getGenres() != null
                                ? film.getGenres().stream()
                                  .sorted(Comparator.comparingLong(Genre::getId))
                                  .map(g -> GenreDto.builder()
                                            .id(g.getId())
                                            .name(g.getName())
                                            .build())
                                  .toList()
                                : List.of()
                )
                .directors(
                        film.getDirectors() != null
                                ? film.getDirectors().stream()
                                  .sorted(Comparator.comparingLong(Director::getId))
                                  .map(d -> DirectorDto.builder()
                                            .id(d.getId())
                                            .name(d.getName())
                                            .build())
                                  .toList()
                                : List.of()
                )
                .build();
    }

    public static void updateFilmFields(Film film, UpdateFilmRequest request) {
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());
        film.setMpa(request.getMpa());
        film.setGenres(request.getGenres());
        film.setDirectors(request.getDirectors());
    }
}