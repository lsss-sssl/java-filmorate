package ru.yandex.practicum.filmorate.exception.filmException;

import ru.yandex.practicum.filmorate.exception.ValidationException;

public class EmptyNameException extends ValidationException {
    public EmptyNameException() {
        super("Название не может быть пустым");
    }
}
