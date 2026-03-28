package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.filmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.userStorage.UserStorage;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> getAll() {
        return filmStorage.findAll();
    }

    public Film getById(final Long id) {
        Film film = filmStorage.findById(id);
        if (film == null) {
            throw new NotFoundException("Film not found");
        }
        return film;
    }

    public List<Film> getPopular(final int amount) {
        List<Film> popular = filmStorage.findAll().stream()
                .sorted(Comparator.comparing((Film film) -> film.getLikes().size()).reversed())
                .limit(amount)
                .toList();
        log.info("TOP {} CREATED: ids={}", amount, popular.stream()
                .map(Film::getId)
                .toList());
        return popular;
    }

    public Film create(final Film newFilm) {
        Film film = filmStorage.add(newFilm);
        log.info("FILM CREATED: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    public Film update(final Film newFilm) {
        final Film oldFilm = filmStorage.findById(newFilm.getId());
        if (oldFilm == null) {
            log.warn("INVALID ID: id={}", newFilm.getId());
            throw new NotFoundException("Film not found");
        }
        oldFilm.setName(newFilm.getName());
        oldFilm.setDescription(newFilm.getDescription());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        oldFilm.setDuration(newFilm.getDuration());
        log.info("FILM UPDATED: id={}, name={}", oldFilm.getId(), oldFilm.getName());
        return oldFilm;
    }

//    public void like(final Long filmId, final Long userId) {
//        Film film = filmStorage.findById(filmId);
//        if (film == null) {
//            throw new NotFoundException("Film not found");
//        }
//        User user = userStorage.findById(userId);
//        if (user == null) {
//            throw new NotFoundException("User not found");
//        }
//        film.getLikes().add(userId);
//        log.info("FILM LIKED: filmId={}, UserId={}", filmId, userId);
//    }

//    public void dislike(final Long filmId, final Long userId) {
//        Film film = filmStorage.findById(filmId);
//        if (film == null) {
//            throw new NotFoundException("Film not found");
//        }
//        User user = userStorage.findById(userId);
//        if (user == null) {
//            throw new NotFoundException("User not found");
//        }
//        film.getLikes().remove(userId);
//        log.info("FILM DISLIKED: filmId={}, UserId={}", filmId, userId);
//    }
}