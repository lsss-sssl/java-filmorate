package ru.yandex.practicum.filmorate.exception.userException;

import ru.yandex.practicum.filmorate.exception.ValidationException;

public class InvalidLoginException extends ValidationException {
    public InvalidLoginException() {
        super("Логин не может быть пустым и содержать пробелы");
    }
}
