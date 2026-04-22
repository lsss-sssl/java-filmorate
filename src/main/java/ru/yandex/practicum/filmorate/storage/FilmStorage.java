package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> findAll();

    Optional<Film> findById(long filmId);

    Film save(Film film);

    Film update(Film film);

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    List<Film> findPopular(long count, Integer genreId, Integer year);

    List<Film> findPopular(long count);

    List<Film> findByDirectorIdOrderByYear(long directorId);

    List<Film> findByDirectorIdOrderByLikes(long directorId);

    void deleteById(long filmId);

    List<Film> findRecommendationsByUserId(long userId);

    List<Film> search(String query, boolean searchByTitle, boolean searchByDirector);
}