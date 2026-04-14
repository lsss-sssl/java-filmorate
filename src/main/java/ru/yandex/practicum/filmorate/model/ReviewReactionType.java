package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewReactionType {
    LIKE(1),
    DISLIKE(-1);

    private final long delta;
}
