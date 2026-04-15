package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewStorage {
    List<Review> findAll(long count);

    Optional<Review> findById(long reviewId);

    List<Review> findAllByFilmId(long filmId, long count);

    Review save(Review review);

    Review update(Review review);

    void delete(long reviewId);
}