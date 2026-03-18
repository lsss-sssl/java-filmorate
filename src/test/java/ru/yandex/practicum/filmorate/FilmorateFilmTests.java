package ru.yandex.practicum.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FilmorateFilmTests {

    private Validator validator;
    private Film film;

    @BeforeEach
    void update() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        film = new Film();
        film.setName("film");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(90);
    }

    @Test
    void shouldFail_whenNameIsBlank() {
        film.setName("");
        assertThat(validator.validate(film)).isNotEmpty();
    }

    @Test
    void shouldFail_whenDescriptionIsTooLong() {
        film.setDescription("a".repeat(201));
        assertThat(validator.validate(film)).isNotEmpty();
    }

    @Test
    void shouldFail_whenInvalidReleaseDate() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThat(validator.validate(film)).isNotEmpty();
    }

    @Test
    void shouldPass_whenReleaseDateIs_1895_12_28() {
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        assertThat(validator.validate(film)).isEmpty();
    }

    @Test
    void shouldFail_whenReleaseDateIsNull() {
        film.setReleaseDate(null);
        assertThat(validator.validate(film)).isNotEmpty();
    }

    @Test
    void shouldFail_whenDurationIsNegative() {
        film.setDuration(-1);
        assertThat(validator.validate(film)).isNotEmpty();
    }

    @Test
    void shouldPass_validFilm() {
        assertThat(validator.validate(film)).isEmpty();
    }
}
