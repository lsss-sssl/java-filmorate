package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.review.NewReviewRequest;
import ru.yandex.practicum.filmorate.dto.review.ReviewDto;
import ru.yandex.practicum.filmorate.dto.review.UpdateReviewRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.model.ReactionType;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.ReviewReactionStorage;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.mapper.EventMapper;
import ru.yandex.practicum.filmorate.storage.EventStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewStorage reviewStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final ReviewReactionStorage reactionStorage;
    private final EventStorage eventStorage;

    public ReviewDto getById(final long reviewId) {
        log.info("Request to get review by id={}", reviewId);
        return ReviewMapper.mapToReviewDto(findByIdOrThrow(reviewId));
    }

    public List<ReviewDto> getAll(Long filmId, long count) {
        log.info("Request to get reviews for film: filmId={}", filmId);
        long limit = count > 0 ? count : 10;
        if (filmId != null) ensureFilmExists(filmId);
        List<Review> reviews = filmId == null
                ? reviewStorage.findAll(limit)
                : reviewStorage.findAllByFilmId(filmId, limit);
        return reviews.stream()
                .map(ReviewMapper::mapToReviewDto)
                .collect(Collectors.toList());
    }

    public ReviewDto create(NewReviewRequest request) {
        log.info("Creating review: filmId={}, isPositive={}", request.getFilmId(), request.isPositive());
        ensureUserExists(request.getUserId());
        ensureFilmExists(request.getFilmId());
        Review review = ReviewMapper.mapToReview(request);
        Review savedReview = reviewStorage.save(review);
        eventStorage.save(EventMapper.mapToEvent(savedReview.getUserId(), "REVIEW", "ADD", savedReview.getId()));
        ReviewDto reviewDto = ReviewMapper.mapToReviewDto(savedReview);
        log.info("Review created: id={}", reviewDto.getReviewId());
        return reviewDto;
    }

    public ReviewDto update(UpdateReviewRequest request) {
        log.info("Updating review: id={}", request.getReviewId());
        Review oldReview = findByIdOrThrow(request.getReviewId());
        ReviewMapper.updateReviewFields(oldReview, request);
        reviewStorage.update(oldReview);
        eventStorage.save(EventMapper.mapToEvent(oldReview.getUserId(), "REVIEW", "UPDATE", oldReview.getId()));
        log.info("Review updated: id={}", oldReview.getId());
        return ReviewMapper.mapToReviewDto(oldReview);
    }

    public void like(final long reviewId, final long userId) {
        log.info("Adding like: reviewId={}, userId={}", reviewId, userId);
        classifyReaction(reviewId, userId, ReactionType.LIKE);
        log.info("Like added: reviewId={}, userId={}", reviewId, userId);
    }

    public void dislike(final long reviewId, final long userId) {
        log.info("Adding dislike: reviewId={}, userId={}", reviewId, userId);
        classifyReaction(reviewId, userId, ReactionType.DISLIKE);
        log.info("Dislike added: reviewId={}, userId={}", reviewId, userId);
    }

    public void deleteLike(final long reviewId, final long userId) {
        log.info("Removing like: reviewId={}, userId={}", reviewId, userId);
        removeReaction(reviewId, userId, ReactionType.LIKE);
        log.info("Like removed: reviewId={}, userId={}", reviewId, userId);
    }

    public void deleteDislike(final long reviewId, final long userId) {
        log.info("Removing dislike: reviewId={}, userId={}", reviewId, userId);
        removeReaction(reviewId, userId, ReactionType.DISLIKE);
        log.info("Dislike removed: reviewId={}, userId={}", reviewId, userId);
    }

    public void delete(final long reviewId) {
        log.info("Deleting review: reviewId={}", reviewId);
        Review review = findByIdOrThrow(reviewId);
        reviewStorage.delete(reviewId);
        eventStorage.save(EventMapper.mapToEvent(review.getUserId(), "REVIEW", "REMOVE", reviewId));
        log.info("Review deleted: reviewId={}", reviewId);
    }

    private void classifyReaction(final long reviewId, final long userId, ReactionType reactionType) {
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        Optional<Boolean> reaction = reactionStorage.findReaction(reviewId, userId);
        if (reaction.isEmpty()) {
            reactionStorage.addReaction(reviewId, userId, reactionType.isLike());
            return;
        }
        if (reaction.get() != reactionType.isLike()) {
            reactionStorage.updateReaction(reviewId, userId, reactionType.isLike());
        }
    }

    private void removeReaction(final long reviewId, final long userId, ReactionType reactionType) {
        findByIdOrThrow(reviewId);
        ensureUserExists(userId);
        reactionStorage.deleteReaction(reviewId, userId, reactionType.isLike());
    }

    private Review findByIdOrThrow(final long reviewId) {
        return reviewStorage.findById(reviewId).orElseThrow(() -> new NotFoundException("Review not found by id=" + reviewId));
    }

    private void ensureFilmExists(final long filmId) {
        filmStorage.findById(filmId).orElseThrow(() -> new NotFoundException("Film not found by id=" + filmId));
    }

    private void ensureUserExists(final long userId) {
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("User not found by id=" + userId));
    }
}