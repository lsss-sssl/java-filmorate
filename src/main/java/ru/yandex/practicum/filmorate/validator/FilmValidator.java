package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.filmException.EmptyNameException;
import ru.yandex.practicum.filmorate.exception.filmException.LongDescriptionException;
import ru.yandex.practicum.filmorate.exception.filmException.NegativeDurationException;
import ru.yandex.practicum.filmorate.exception.filmException.ReleaseDateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator implements Validator<Film> {

    private static final int MAX_LENGTH = 200;
    private static final LocalDate FIRST_FILM_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) throw new EmptyNameException();
        if (film.getDescription() != null && film.getDescription().length() > MAX_LENGTH) throw new LongDescriptionException();
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(FIRST_FILM_DATE)) throw new ReleaseDateException();
        if (film.getDuration() == null || film.getDuration() < 0) throw new NegativeDurationException();
    }
}
