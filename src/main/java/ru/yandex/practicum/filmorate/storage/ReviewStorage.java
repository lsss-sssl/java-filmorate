package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewStorage {
    Review save(Review review);

    Review update(Review review);

    Optional<Review> findById(long reviewId);

    void delete(long reviewId);

    List<Review> findByFilmId(long filmId, int count);

    List<Review> findAll(int count);

    Optional<Boolean> findReaction(long reviewId, long userId);

    void addReaction(long reviewId, long userId, boolean isLike);

    void updateReaction(long reviewId, long userId, boolean isLike);

    void deleteReaction(long reviewId, long userId);
}