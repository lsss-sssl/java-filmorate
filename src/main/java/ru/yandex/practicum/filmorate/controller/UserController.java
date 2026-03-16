package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     *     GET    /users
     *     GET    /users/{id}
     *     GET    /users/{id}/friends
     *     GET    /users/{id}/friends/common/{otherId}
     *     POST   /users
     *     PUT    /users
     *     PUT    /users/{id}/friends/{friendId}
     *     DELETE /users/{id}/friends/{friendId}
     */

    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable final Long id) {
        return userService.getById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsById(@PathVariable final Long id) {
        return userService.getFriendsById(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable final Long id,
                                       @PathVariable final Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User newUser) {
        return userService.create(newUser);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        return userService.update(newUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void makeFriend(@PathVariable final Long id,
                           @PathVariable final Long friendId) {
        userService.makeFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable final Long id,
                             @PathVariable final Long friendId) {
        userService.removeFriend(id, friendId);
    }
}