package ru.yandex.practicum.filmorate.storage.filmStorage;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;

public interface FilmStorage {

    Film add(final Film film);

    Film findById(final Long filmId);

    List<Film> findAll();
}
