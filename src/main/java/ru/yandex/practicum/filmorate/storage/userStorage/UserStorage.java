package ru.yandex.practicum.filmorate.storage.userStorage;

import ru.yandex.practicum.filmorate.model.user.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    List<User> findAll();
    Optional<User> findById(long userId);
    List<User> findFriendsById(long userId);
    List<User> findCommonFriendsById(long userId, long friendId);
    User save(User user);
    User update(User user);
    void classifyFriendship(long userId, long friendId, FriendshipStatus status);
    void endFriendship(long userId, long friendId);
    Optional<Long> findFriendshipStatus(long userId, long friendId);
}
