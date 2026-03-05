package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final AtomicLong lastId = new AtomicLong();
    private final Map<Long, Film> storage = new HashMap<>();

    @Override
    public Film add(final Film film) {
        film.setId(lastId.incrementAndGet());
        storage.put(film.getId(), film);
        return film;
    }

    @Override
    public Film findById(final Long id) {
        return storage.get(id);
    }

    @Override
    public List<Film> findAll() {
        return List.copyOf(storage.values());
    }
}
