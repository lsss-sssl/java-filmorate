package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorStorage {
    List<Director> findAll();

    Optional<Director> findById(long directorId);

    Director save(Director director);

    Director update(Director director);

    void deleteById(long directorId);
}