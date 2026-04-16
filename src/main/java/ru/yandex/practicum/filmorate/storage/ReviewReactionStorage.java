package ru.yandex.practicum.filmorate.storage;

import java.util.Optional;

public interface ReviewReactionStorage {
    Optional<Boolean> findReaction(long reviewId, long userId);

    void addReaction(long reviewId, long userId, boolean isLike);

    void updateReaction(long reviewId, long userId, boolean isLike);

    void deleteReaction(long reviewId, long userId, boolean isLike);
}