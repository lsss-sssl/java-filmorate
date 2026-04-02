package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.user.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public List<UserDto> getAll() {
        return userStorage.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getById(final long userId) {
        return UserMapper.mapToUserDto(findByIdOrThrow(userId));
    }

    public List<UserDto> getFriendsById(final long userId) {
        findByIdOrThrow(userId);
        return userStorage.findFriendsById(userId).stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getCommonFriends(final Long userId, final Long friendId) {
        findByIdOrThrow(userId);
        findByIdOrThrow(friendId);
        return userStorage.findCommonFriendsById(userId, friendId).stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto create(NewUserRequest request) {
        User user = UserMapper.mapToUser(request);
        return UserMapper.mapToUserDto(userStorage.save(user));
    }

    public UserDto update(UpdateUserRequest request) {
        User oldUser = findByIdOrThrow(request.getId());
        UserMapper.updateUserFields(oldUser, request);
        userStorage.update(oldUser);
        return UserMapper.mapToUserDto(oldUser);
    }

    @Transactional
    public void addFriend(final long userId, final long friendId) {
        findByIdOrThrow(userId);
        findByIdOrThrow(friendId);
        Optional<Long> userToFriendStatus = userStorage.findFriendshipStatus(userId, friendId);
        Optional<Long> friendToUserStatus = userStorage.findFriendshipStatus(friendId, userId);
        if (userToFriendStatus.isPresent()) {
            return;
        }
        if (friendToUserStatus.isPresent() && friendToUserStatus.get() == FriendshipStatus.UNCONFIRMED.getId()) {
            userStorage.classifyFriendship(userId, friendId, FriendshipStatus.CONFIRMED);
            userStorage.classifyFriendship(friendId, userId, FriendshipStatus.CONFIRMED);
        } else {
            userStorage.classifyFriendship(userId, friendId, FriendshipStatus.UNCONFIRMED);
        }
    }

    @Transactional
    public void removeFriend(final long userId, final long friendId) {
        findByIdOrThrow(userId);
        findByIdOrThrow(friendId);
        Optional<Long> userToFriendStatus = userStorage.findFriendshipStatus(userId, friendId);
        if (userToFriendStatus.isEmpty()) {
            return;
        }
        if (userToFriendStatus.get() == FriendshipStatus.CONFIRMED.getId()) {
            userStorage.classifyFriendship(friendId, userId, FriendshipStatus.UNCONFIRMED);
        }
        userStorage.endFriendship(userId, friendId);
    }

    private User findByIdOrThrow(final long userId) {
        return userStorage.findById(userId).orElseThrow(() -> new NotFoundException("User not found by id=" + userId));
    }
}