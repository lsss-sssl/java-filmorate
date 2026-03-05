package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final AtomicLong lastId = new AtomicLong();
    private final Map<Long, User> storage = new HashMap<>();

    @Override
    public User add(final User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(lastId.incrementAndGet());
        storage.put(user.getId(), user);
        return user;
    }

    @Override
    public User findById(final Long id) {
        return storage.get(id);
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(storage.values());
    }
}
