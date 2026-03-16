package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User add(final User user);

    User findById(final Long userId);

    List<User> findAll();
}
