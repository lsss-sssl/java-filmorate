package ru.yandex.practicum.filmorate.exception.userException;

import ru.yandex.practicum.filmorate.exception.ValidationException;

public class BirthdayException extends ValidationException {
    public BirthdayException() {
        super("Дата рождения не может быть в будущем.");
    }
}
