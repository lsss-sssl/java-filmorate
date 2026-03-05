package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film add(final Film film);

    Film findById(final Long filmId);

    List<Film> findAll();
}
