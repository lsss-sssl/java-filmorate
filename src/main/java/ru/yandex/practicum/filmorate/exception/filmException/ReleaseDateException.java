package ru.yandex.practicum.filmorate.exception.filmException;

import ru.yandex.practicum.filmorate.exception.ValidationException;

public class ReleaseDateException extends ValidationException {
    public ReleaseDateException() {
        super("Дата релиза — не раньше 28 декабря 1895 года");
    }
}
