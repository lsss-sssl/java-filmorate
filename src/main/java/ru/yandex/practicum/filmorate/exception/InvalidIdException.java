package ru.yandex.practicum.filmorate.exception;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException() {
        super("Id должен быть указан");
    }
}
