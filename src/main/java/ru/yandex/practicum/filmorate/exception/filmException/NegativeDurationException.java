package ru.yandex.practicum.filmorate.exception.filmException;

import ru.yandex.practicum.filmorate.exception.ValidationException;

public class NegativeDurationException extends ValidationException {
    public NegativeDurationException() {
        super("Продолжительность фильма должна быть положительным числом.");
    }
}
