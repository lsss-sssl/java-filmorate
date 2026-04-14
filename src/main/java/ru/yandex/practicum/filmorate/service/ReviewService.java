package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.review.NewReviewRequest;
import ru.yandex.practicum.filmorate.dto.review.ReviewDto;
import ru.yandex.practicum.filmorate.dto.review.UpdateReviewRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.model.Review;
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
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public ReviewDto getById(final long reviewId) {
        log.debug("Request to get review by id={}", reviewId);
        return ReviewMapper.mapToReviewDto(findByIdOrThrow(reviewId));
    }

    public List<ReviewDto> getAllByFilmId(final long filmId, final long count) {
        log.debug("Request to get reviews for film");
        ensureFilmExists(filmId);
        return reviewStorage.findAllByFilmId(filmId, count).stream()
                .map(ReviewMapper::mapToReviewDto)
                .collect(Collectors.toList());
    }

    public ReviewDto create(NewReviewRequest request) {
        log.info("Creating review: filmId={}, isPositive={}", request.getFilmId(), request.isPositive());
        Review review = ReviewMapper.mapToReview(request);
        ReviewDto reviewDto = ReviewMapper.mapToReviewDto(reviewStorage.save(review));
        log.info("Review created: id={}", reviewDto.getId());
        return reviewDto;
    }

    public ReviewDto update(UpdateReviewRequest request) {
        log.info("Updating review: id={}", request.getId());
        Review oldReview = findByIdOrThrow(request.getId());
        ReviewMapper.updateReviewFields(oldReview, request);
        reviewStorage.update(oldReview);
        log.info("Review updated: id={}", oldReview.getId());
        return ReviewMapper.mapToReviewDto(oldReview);
    }

    public void like(final long reviewId, final long userId) {
        log.info("Adding like: reviewId={}, userId={}", reviewId, userId);
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        reviewStorage.addLike(reviewId, userId);
        log.info("Like added: reviewId={}, userId={}", reviewId, userId);
    }

    public void dislike(final long reviewId, final long userId) {
        log.info("Adding dislike: reviewId={}, userId={}", reviewId, userId);
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        reviewStorage.addDislike(reviewId, userId);
        log.info("Dislike added: reviewId={}, userId={}", reviewId, userId);
    }

    public void deleteLike(final long reviewId, final long userId) {
        log.info("Removing like: reviewId={}, userId={}", reviewId, userId);
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        reviewStorage.deleteLike(reviewId, userId);
        log.info("Like removed: reviewId={}, userId={}", reviewId, userId);
    }

    public void deleteDislike(final long reviewId, final long userId) {
        log.info("Removing dislike: reviewId={}, userId={}", reviewId, userId);
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        reviewStorage.deleteDislike(reviewId, userId);
        log.info("Dislike removed: reviewId={}, userId={}", reviewId, userId);
    }

    public void delete(final long reviewId) {
        log.info("Deleting review: reviewId={}", reviewId);
        findByIdOrThrow(reviewId);
        reviewStorage.delete(reviewId);
        log.info("Review deleted: reviewId={}", reviewId);
    }

    private Review findByIdOrThrow(final long reviewId) {
        return reviewStorage.findById(reviewId).orElseThrow(() -> new NotFoundException("Review not found by id=" + reviewId));
    }

    private void ensureFilmExists(final long filmId) {
        filmStorage.findById(filmId).orElseThrow(() -> new NotFoundException("User not found by id=" + filmId));
    }

    private void ensureUserExists(final long userId) {
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("User not found by id=" + userId));
    }
}