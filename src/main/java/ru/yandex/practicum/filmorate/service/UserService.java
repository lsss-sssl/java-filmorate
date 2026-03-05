package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public List<User> getAll() {
        return userStorage.findAll();
    }

    public User getById(final Long id) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new NotFoundException("Film not found");
        }
        return user;
    }

    public List<User> getFriendsById(final Long userId) {
        User user = userStorage.findById(userId);
        if (user == null) {
            throw new NotFoundException("Film not found");
        }
        List<User> friends = user.getFriends().stream()
                .map(userStorage::findById)
                .toList();
        log.info("FOUND FRIENDS: userId={}, friendsIds={}", userId, friends.stream()
                .map(User::getId)
                .toList());
        return friends;
    }

    public List<User> getCommonFriends(final Long id, final Long otherId) {
        User user = getById(id);
        User other = getById(otherId);

        return user.getFriends().stream()
                .filter(other.getFriends()::contains)
                .map(this::getById)
                .toList();
    }

    public User create(final User newUser) {
        User user = userStorage.add(newUser);
        log.info("USER CREATED: id={}, login={}", user.getId(), user.getLogin());
        return user;
    }

    public User update(User newUser) {
        User oldUser = userStorage.findById(newUser.getId());

        if (oldUser == null) {
            log.warn("INVALID ID: id={}", newUser.getId());
            throw new NotFoundException("User not found");
        }
        oldUser.setEmail(newUser.getEmail());
        oldUser.setLogin(newUser.getLogin());
        oldUser.setName(newUser.getName() == null || newUser.getName().isBlank() ? newUser.getLogin() : newUser.getName());
        oldUser.setBirthday(newUser.getBirthday());
        log.info("USER UPDATED: id={}, login={}", newUser.getId(), newUser.getLogin());
        return oldUser;
    }


    public void makeFriend(final Long id, final Long friendId) {
        getById(id).getFriends().add(friendId);
        getById(friendId).getFriends().add(id);
    }

    public void removeFriend(final Long id, final Long friendId) {
        getById(id).getFriends().remove(friendId);
        getById(friendId).getFriends().remove(id);
    }
}