package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.Film;

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
    private final AtomicLong lasrId = new AtomicLong();

    @GetMapping
    public Collection<Film> getFilms() {
        return List.copyOf(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        film.setId(lasrId.incrementAndGet());
        films.put(film.getId(), film);
        log.info("FILM CREATED: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        Film oldFilm = films.get(newFilm.getId());
        if (oldFilm == null) {
            log.warn("INVALID ID: id={}", newFilm.getId());
            throw new InvalidIdException();
        }
        oldFilm.setName(newFilm.getName());
        oldFilm.setDescription(newFilm.getDescription());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        oldFilm.setDuration(newFilm.getDuration());
        log.info("FILM UPDATED: id={}, name={}", oldFilm.getId(), oldFilm.getName());
        return oldFilm;
    }
}
