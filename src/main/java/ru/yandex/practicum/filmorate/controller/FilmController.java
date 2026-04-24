package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public final class FilmController {
    private final FilmService filmService;

    @GetMapping
    public List<FilmDto> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public FilmDto getById(@PathVariable long id) {
        return filmService.getById(id);
    }

    @GetMapping("/popular")
    public List<FilmDto> getPopular(
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) Integer genreId,
            @RequestParam(required = false) Integer year) {
        return filmService.getPopular(count, genreId, year);
    }

    @GetMapping("/director/{directorId}")
    public List<FilmDto> getByDirector(
            @PathVariable Long directorId,
            @RequestParam String sortBy) {
        return filmService.getByDirector(directorId, sortBy);
    }

    @GetMapping("/search")
    public List<FilmDto> searchFilms(
            @RequestParam String query,
            @RequestParam(defaultValue = "title") String by) {
        return filmService.searchFilms(query, by);
    }

    @GetMapping("/common")
    public List<FilmDto> searchCommonFilms(@RequestParam Long userId,
                                           @RequestParam Long friendId) {
        return filmService.searchCommonFilms(userId, friendId);
    }

    @PostMapping
    public FilmDto create(@Valid @RequestBody NewFilmRequest request) {
        return filmService.create(request);
    }

    @PutMapping
    public FilmDto update(@Valid @RequestBody UpdateFilmRequest request) {
        return filmService.update(request);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void like(@PathVariable final long filmId,
                     @PathVariable final long userId) {
        filmService.like(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void dislike(@PathVariable final long filmId,
                        @PathVariable final long userId) {
        filmService.dislike(filmId, userId);
    }

    @DeleteMapping("/{filmId}")
    public void deleteFilm(@PathVariable long filmId) {

        filmService.deleteFilm(filmId);
    }
}