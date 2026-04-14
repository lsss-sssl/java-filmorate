package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewStorage {
    List<Review> findAll(long count);

    Optional<Review> findById(long reviewId);

    List<Review> findAllByFilmId(long reviewId, long count);

    Review save(Review review);

    Review update(Review review);

    void addLike(long reviewId, long userId);

    void deleteLike(long reviewId, long userId);

    void addDislike(long reviewId, long userId);

    void deleteDislike(long reviewId, long userId);

    void delete(long reviewId);
}