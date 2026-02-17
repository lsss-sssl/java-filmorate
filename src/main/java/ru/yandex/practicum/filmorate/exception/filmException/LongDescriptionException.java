package ru.yandex.practicum.filmorate.exception.filmException;

import ru.yandex.practicum.filmorate.exception.ValidationException;

public class LongDescriptionException extends ValidationException {
    public LongDescriptionException() {
        super("Максимальная длина описания — 200 символов");
    }
}
