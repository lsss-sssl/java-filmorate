package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    public List<UserDto> getAll() {
        log.debug("Request to get all users");
        return userStorage.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getById(final long userId) {
        log.debug("Request to get user by id={}", userId);
        return UserMapper.mapToUserDto(findByIdOrThrow(userId));
    }

    public List<FilmDto> getRecommendations(final long userId) {
        log.debug("Request to get recommendations by userId={}", userId);
        findByIdOrThrow(userId);
        return filmStorage.findRecommendationsByUserId(userId).stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getFriendsById(final long userId) {
        log.debug("Request to get friends by userId={}", userId);
        findByIdOrThrow(userId);
        return userStorage.findFriendsById(userId).stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getCommonFriends(final Long userId, final Long friendId) {
        log.debug("Request to get common friends: userId={}, friendId={}", userId, friendId);
        findByIdOrThrow(userId);
        findByIdOrThrow(friendId);
        return userStorage.findCommonFriendsById(userId, friendId).stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto create(NewUserRequest request) {
        log.info("Creating user: email={}, login={}", request.getEmail(), request.getLogin());
        User user = UserMapper.mapToUser(request);
        UserDto userDto = UserMapper.mapToUserDto(userStorage.save(user));
        log.info("User created: id={}", userDto.getId());
        return userDto;
    }

    public UserDto update(UpdateUserRequest request) {
        log.info("Updating user: id={}", request.getId());
        User oldUser = findByIdOrThrow(request.getId());
        UserMapper.updateUserFields(oldUser, request);
        userStorage.update(oldUser);
        log.info("User updated: id={}", oldUser.getId());
        return UserMapper.mapToUserDto(oldUser);
    }

    @Transactional
    public void addFriend(final long userId, final long friendId) {
        log.info("Adding friend: userId={}, friendId={}", userId, friendId);
        findByIdOrThrow(userId);
        findByIdOrThrow(friendId);
        Optional<Long> userToFriendStatus = userStorage.findFriendshipStatus(userId, friendId);
        Optional<Long> friendToUserStatus = userStorage.findFriendshipStatus(friendId, userId);
        if (userToFriendStatus.isPresent()) {
            log.debug("Friendship already exists: userId={}, friendId={}", userId, friendId);
            return;
        }
        if (friendToUserStatus.isPresent() && friendToUserStatus.get() == FriendshipStatus.UNCONFIRMED.getId()) {
            userStorage.classifyFriendship(userId, friendId, FriendshipStatus.CONFIRMED);
            userStorage.classifyFriendship(friendId, userId, FriendshipStatus.CONFIRMED);
            log.info("Friendship confirmed: userId={}, friendId={}", userId, friendId);
        } else {
            userStorage.classifyFriendship(userId, friendId, FriendshipStatus.UNCONFIRMED);
            log.info("Friend request created: userId={}, friendId={}", userId, friendId);
        }
    }

    @Transactional
    public void removeFriend(final long userId, final long friendId) {
        log.info("Removing friend: userId={}, friendId={}", userId, friendId);
        findByIdOrThrow(userId);
        findByIdOrThrow(friendId);
        Optional<Long> userToFriendStatus = userStorage.findFriendshipStatus(userId, friendId);
        if (userToFriendStatus.isEmpty()) {
            log.debug("Friendship does not exist: userId={}, friendId={}", userId, friendId);
            return;
        }
        if (userToFriendStatus.get() == FriendshipStatus.CONFIRMED.getId()) {
            userStorage.classifyFriendship(friendId, userId, FriendshipStatus.UNCONFIRMED);
        }
        userStorage.endFriendship(userId, friendId);
        log.info("Friend removed: userId={}, friendId={}", userId, friendId);
    }

    private User findByIdOrThrow(final long userId) {
        return userStorage.findById(userId).orElseThrow(() -> new NotFoundException("User not found by id=" + userId));
    }
}