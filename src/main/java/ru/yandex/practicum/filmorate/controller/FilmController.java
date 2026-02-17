package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();
    private final Validator<Film> validator = new FilmValidator();
    private final AtomicLong lasrId = new AtomicLong();

    @GetMapping
    public Collection<Film> getFilms() {
        return List.copyOf(films.values());
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        try {
            validator.validate(film);
        } catch (ValidationException e) {
            log.warn("VALIDATION ERROR: {}", e.getMessage());
            throw e;
        }
        film.setId(lasrId.incrementAndGet());
        films.put(film.getId(), film);
        log.info("FILM CREATED: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        try {
            validator.validate(newFilm);
        } catch (ValidationException e) {
            log.warn("UPDATE VALIDATION ERROR: {}", e.getMessage());
            throw e;
        }
        if (newFilm.getId() == null || !films.containsKey(newFilm.getId())) {
            log.warn("INVALID ID: id={}", newFilm.getId());
            throw new InvalidIdException();
        }
        films.computeIfPresent(newFilm.getId(), (id, oldFilm) -> {
            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            return oldFilm;
        });
        log.info("FILM UPDATED: id={}, name={}", newFilm.getId(), newFilm.getName());
        return films.get(newFilm.getId());
    }
}
