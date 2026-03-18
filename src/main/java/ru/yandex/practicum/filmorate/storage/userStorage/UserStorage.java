package ru.yandex.practicum.filmorate.storage.userStorage;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

public interface UserStorage {

    User add(final User user);

    User findById(final Long userId);

    List<User> findAll();
}
