package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong lastId = new AtomicLong();

    @GetMapping
    public Collection<User> getUsers() {
        return List.copyOf(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(lastId.incrementAndGet());
        users.put(user.getId(), user);
        log.info("USER CREATED: id={}, login={}", user.getId(), user.getLogin());
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        User oldUser = users.get(newUser.getId());
        if (oldUser == null) {
            log.warn("INVALID ID: id={}", newUser.getId());
            throw new InvalidIdException();
        }
        oldUser.setEmail(newUser.getEmail());
        oldUser.setLogin(newUser.getLogin());
        oldUser.setName(newUser.getName() == null || newUser.getName().isBlank() ? newUser.getLogin() : newUser.getName());
        oldUser.setBirthday(newUser.getBirthday());
        log.info("USER UPDATED: id={}, login={}", newUser.getId(), newUser.getLogin());
        return oldUser;
    }
}