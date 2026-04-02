package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<FilmDto> getAll() {
        return filmStorage.findAll().stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public FilmDto getById(final long filmId) {
        return FilmMapper.mapToFilmDto(findByIdOrThrow(filmId));
    }

    public List<FilmDto> getPopular(final int count) {
        return filmStorage.findPopular(count).stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public FilmDto create(NewFilmRequest request) {
        Film film = FilmMapper.mapToFilm(request);
        return FilmMapper.mapToFilmDto(filmStorage.save(film));
    }

    public FilmDto update(UpdateFilmRequest request) {
        Film oldFilm = findByIdOrThrow(request.getId());
        FilmMapper.updateFilmFields(oldFilm, request);
        filmStorage.update(oldFilm);
        return FilmMapper.mapToFilmDto(oldFilm);
    }

    public void like(final long filmId, final long userId) {
        findByIdOrThrow(filmId);
        ensureUserExists(userId);
        filmStorage.addLike(filmId, userId);
    }

    public void dislike(final long filmId, final long userId) {
        findByIdOrThrow(filmId);
        ensureUserExists(userId);
        filmStorage.deleteLike(filmId, userId);
    }

    private Film findByIdOrThrow(final long filmId) {
        return filmStorage.findById(filmId).orElseThrow(() -> new NotFoundException("Film not found by id=" + filmId));
    }

    private void ensureUserExists(final long userId) {
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("User not found by id=" + userId));
    }
}