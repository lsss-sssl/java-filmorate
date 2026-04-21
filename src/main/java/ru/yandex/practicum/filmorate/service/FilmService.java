package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.mapper.EventMapper;
import ru.yandex.practicum.filmorate.storage.EventStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final DirectorStorage directorStorage;
    private final EventStorage eventStorage;

    public List<FilmDto> getAll() {
        log.info("Request to get all films");
        return filmStorage.findAll().stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public FilmDto getById(final long filmId) {
        log.info("Request to get film by id={}", filmId);
        return FilmMapper.mapToFilmDto(findByIdOrThrow(filmId));
    }

    public List<FilmDto> getPopular(int count, Integer genreId, Integer year) {
        log.info("Request to get popular films: count={}, genreId={}, year={}", count, genreId, year);
        return filmStorage.findPopular(count, genreId, year).stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public List<FilmDto> getByDirector(final long directorId, String sortBy) {
        log.info("Request to get film by directorId={}, sortBy={}", directorId, sortBy);
        ensureDirectorExists(directorId);
        List<Film> films = switch (sortBy) {
            case "year" -> filmStorage.findByDirectorIdOrderByYear(directorId);
            case "likes" -> filmStorage.findByDirectorIdOrderByLikes(directorId);
            default -> throw new IllegalArgumentException("Unknown sortBy = " + sortBy);
        };
        return films.stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public FilmDto create(NewFilmRequest request) {
        log.info("Creating film: name={}, releaseDate={}", request.getName(), request.getReleaseDate());
        Film film = FilmMapper.mapToFilm(request);
        FilmDto filmDto = FilmMapper.mapToFilmDto(filmStorage.save(film));
        log.info("Film created: id={}", filmDto.getId());
        return filmDto;
    }

    public FilmDto update(UpdateFilmRequest request) {
        log.info("Updating film: id={}", request.getId());
        Film oldFilm = findByIdOrThrow(request.getId());
        FilmMapper.updateFilmFields(oldFilm, request);
        filmStorage.update(oldFilm);
        log.info("Film updated: id={}", oldFilm.getId());
        return FilmMapper.mapToFilmDto(oldFilm);
    }

    public void like(final long filmId, final long userId) {
        log.info("Adding like: filmId={}, userId={}", filmId, userId);
        findByIdOrThrow(filmId);
        ensureUserExists(userId);
        filmStorage.addLike(filmId, userId);
        eventStorage.save(EventMapper.mapToEvent(userId, "LIKE", "ADD", filmId));
        log.info("Like added: filmId={}, userId={}", filmId, userId);
    }

    public void dislike(final long filmId, final long userId) {
        log.info("Removing like: filmId={}, userId={}", filmId, userId);
        findByIdOrThrow(filmId);
        ensureUserExists(userId);
        filmStorage.deleteLike(filmId, userId);
        eventStorage.save(EventMapper.mapToEvent(userId, "LIKE", "REMOVE", filmId));
        log.info("Like removed: filmId={}, userId={}", filmId, userId);
    }

    private Film findByIdOrThrow(final long filmId) {
        return filmStorage.findById(filmId).orElseThrow(() -> new NotFoundException("Film not found by id=" + filmId));
    }

    private void ensureUserExists(final long userId) {
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("User not found by id=" + userId));
    }

    private void ensureDirectorExists(final long directorId) {
        directorStorage.findById(directorId).orElseThrow(() -> new NotFoundException("Director not found by id=" + directorId));
    }

    @Transactional
    public void deleteFilm(long filmId) {
        log.info("Deleting film: id={}", filmId);
        findByIdOrThrow(filmId);
        filmStorage.deleteById(filmId);
        log.info("Film deleted: id={}", filmId);
    }
}