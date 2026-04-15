package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public final class UserController {
    private final UserService userService;

// TODO:   GET    /users/{id}/feed
//         DELETE /users/{userId}

    /**
     *     GET    /users
     *     GET    /users/{id}
     *     GET    /users/{id}/friends
     *     GET    /users/{id}/friends/common/{friendId}
     *     POST   /users
     *     PUT    /users
     *     PUT    /users/{id}/friends/{friendId}
     *     DELETE /users/{id}/friends/{friendId}
     */

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable final long id) {
        return userService.getById(id);
    }

    @GetMapping("/{id}/recommendations")
    public List<FilmDto> getRecommendations(@PathVariable final long id) {
        return userService.getRecommendations(id);
    }

    @GetMapping("/{id}/friends")
    public List<UserDto> getFriendsById(@PathVariable final long id) {
        return userService.getFriendsById(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public List<UserDto> getCommonFriends(@PathVariable final long id,
                                          @PathVariable final Long friendId) {
        return userService.getCommonFriends(id, friendId);
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody NewUserRequest request) {
        return userService.create(request);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UpdateUserRequest request) {
        return userService.update(request);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void makeFriend(@PathVariable final long id,
                           @PathVariable final long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable final long id,
                             @PathVariable final long friendId) {
        userService.removeFriend(id, friendId);
    }
}