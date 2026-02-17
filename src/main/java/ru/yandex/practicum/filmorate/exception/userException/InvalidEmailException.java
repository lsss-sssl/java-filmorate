package ru.yandex.practicum.filmorate.exception.userException;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Электронная почта не может быть пустой и должна содержать символ @");
    }
}
