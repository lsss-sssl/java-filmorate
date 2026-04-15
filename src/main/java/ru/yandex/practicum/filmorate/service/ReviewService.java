package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.review.NewReviewRequest;
import ru.yandex.practicum.filmorate.dto.review.ReviewDto;
import ru.yandex.practicum.filmorate.dto.review.UpdateReviewRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.EventMapper;
import ru.yandex.practicum.filmorate.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.EventStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewStorage reviewStorage;
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final EventStorage eventStorage;

    public ReviewDto create(NewReviewRequest request) {
        ensureUserExists(request.getUserId());
        ensureFilmExists(request.getFilmId());
        Review review = ReviewMapper.mapToReview(request);
        Review saved = reviewStorage.save(review);
        eventStorage.save(EventMapper.mapToEvent(saved.getUserId(), "REVIEW", "ADD", saved.getReviewId()));
        return ReviewMapper.mapToDto(saved);
    }

    public ReviewDto update(UpdateReviewRequest request) {
        Review old = findByIdOrThrow(request.getReviewId());
        ensureUserExists(request.getUserId());
        ensureFilmExists(request.getFilmId());
        ReviewMapper.updateFields(old, request);
        Review updated = reviewStorage.update(old);
        eventStorage.save(EventMapper.mapToEvent(updated.getUserId(), "REVIEW", "UPDATE", updated.getReviewId()));
        return ReviewMapper.mapToDto(updated);
    }

    public ReviewDto getById(long reviewId) {
        return ReviewMapper.mapToDto(findByIdOrThrow(reviewId));
    }

    public void delete(long reviewId) {
        Review review = findByIdOrThrow(reviewId);
        reviewStorage.delete(reviewId);
        eventStorage.save(EventMapper.mapToEvent(review.getUserId(), "REVIEW", "REMOVE", reviewId));
    }

    public List<ReviewDto> getByFilmId(long filmId, int count) {
        return reviewStorage.findByFilmId(filmId, count).stream()
                .map(ReviewMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ReviewDto> getAll(int count) {
        return reviewStorage.findAll(count).stream()
                .map(ReviewMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDto addLike(long reviewId, long userId) {
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        updateReaction(reviewId, userId, true);
        return getById(reviewId);
    }

    @Transactional
    public ReviewDto addDislike(long reviewId, long userId) {
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        updateReaction(reviewId, userId, false);
        return getById(reviewId);
    }

    @Transactional
    public ReviewDto removeLike(long reviewId, long userId) {
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        reviewStorage.deleteReaction(reviewId, userId);
        return getById(reviewId);
    }

    @Transactional
    public ReviewDto removeDislike(long reviewId, long userId) {
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        reviewStorage.deleteReaction(reviewId, userId);
        return getById(reviewId);
    }

    private void updateReaction(long reviewId, long userId, boolean isLike) {
        var old = reviewStorage.findReaction(reviewId, userId);
        if (old.isEmpty()) {
            reviewStorage.addReaction(reviewId, userId, isLike);
            return;
        }
        if (old.get() != isLike) {
            reviewStorage.updateReaction(reviewId, userId, isLike);
        }
    }

    private Review findByIdOrThrow(long reviewId) {
        return reviewStorage.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found by id=" + reviewId));
    }

    private void ensureUserExists(long userId) {
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("User not found by id=" + userId));
    }

    private void ensureFilmExists(long filmId) {
        filmStorage.findById(filmId).orElseThrow(() -> new NotFoundException("Film not found by id=" + filmId));
    }
}