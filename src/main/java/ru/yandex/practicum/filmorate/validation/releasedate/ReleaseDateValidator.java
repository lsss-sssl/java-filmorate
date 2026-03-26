package ru.yandex.practicum.filmorate.validation.releasedate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    private static final LocalDate FIRST_FILM_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value != null && !value.isBefore(FIRST_FILM_DATE);
    }
}
