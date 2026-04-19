package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface DirectorStorage {
    List<Director> findAll();

    List<Film> findFilmsSorted(Long directorId, String sortBy);

    void deleteById(long directorId);

    Director save(Director director);

    Director update(Director director);

    Optional<Director> findById(long directorId);
}
