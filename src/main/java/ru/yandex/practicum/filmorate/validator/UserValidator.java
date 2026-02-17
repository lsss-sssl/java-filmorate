package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.userException.BirthdayException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidLoginException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) throw new InvalidEmailException();
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) throw new InvalidLoginException();
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) throw new BirthdayException();
    }
}
