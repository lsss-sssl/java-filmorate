package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    /**
     *     GET    /films
     *     GET    /films/{id}
     *     GET    /films/popular?count={count}
     *     POST   /films
     *     PUT    /films
     *     PUT    /films/{id}/like/{userId}
     *     DELETE /films/{id}/like/{userId}
     */

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable final Long id) {
        return filmService.getById(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopular(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        return filmService.update(newFilm);
    }

//    @PutMapping("/{id}/like/{userId}")
//    public void like(@PathVariable final Long id,
//                     @PathVariable final Long userId) {
//        filmService.like(id, userId);
//    }
//
//    @DeleteMapping("/{id}/like/{userId}")
//    public void dislike(@PathVariable final Long id,
//                        @PathVariable final Long userId) {
//        filmService.dislike(id, userId);
//    }
}