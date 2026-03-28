package ru.yandex.practicum.filmorate.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FriendshipStatus {
    CONFIRMED(1),
    UNCONFIRMED(2);

    private final long id;
}
