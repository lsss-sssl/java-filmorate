package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;
import ru.yandex.practicum.filmorate.validator.Validator;

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
    private final Validator<User> validator = new UserValidator();
    private final AtomicLong lastId = new AtomicLong();

    @GetMapping
    public Collection<User> getUsers() {
        return List.copyOf(users.values());
    }

    @PostMapping
    public User create(@RequestBody User user) {
        try {
            validator.validate(user);
        } catch (ValidationException e) {
            log.warn("VALIDATION ERROR: {}", e.getMessage());
            throw e;
        }
        user.setId(lastId.incrementAndGet());
        users.put(user.getId(), user);
        log.info("USER CREATED: id={}, login={}", user.getId(), user.getLogin());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        try {
            validator.validate(newUser);
        } catch (ValidationException e) {
            log.warn("UPDATE VALIDATION ERROR: {}", e.getMessage());
            throw e;
        }
        if (newUser.getId() == null || !users.containsKey(newUser.getId())) {
            log.warn("INVALID ID: id={}", newUser.getId());
            throw new InvalidIdException();
        }
        users.computeIfPresent(newUser.getId(), (id, oldUser) -> {
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            return oldUser;
        });
        log.info("USER UPDATED: id={}, login={}", newUser.getId(), newUser.getLogin());
        return users.get(newUser.getId());
    }
}