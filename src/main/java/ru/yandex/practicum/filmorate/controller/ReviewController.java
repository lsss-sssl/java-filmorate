package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.review.NewReviewRequest;
import ru.yandex.practicum.filmorate.dto.review.ReviewDto;
import ru.yandex.practicum.filmorate.dto.review.UpdateReviewRequest;
import ru.yandex.practicum.filmorate.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public final class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ReviewDto getById(@PathVariable final long id) {
        return reviewService.getById(id);
    }

    @GetMapping
    public List<ReviewDto> getAll(@RequestParam(required = false) Long filmId,
                                  @RequestParam(defaultValue = "10") Long count) {
        return reviewService.getAll(filmId, count);
    }

    @PostMapping
    public ReviewDto create(@Valid @RequestBody NewReviewRequest request) {
        return reviewService.create(request);
    }

    @PutMapping
    public ReviewDto update(@Valid @RequestBody UpdateReviewRequest request) {
        return reviewService.update(request);
    }

    @PutMapping("/{reviewId}/like/{userId}")
    public void like(@PathVariable final long reviewId,
                     @PathVariable final long userId) {
        reviewService.like(reviewId, userId);
    }

    @PutMapping("/{reviewId}/dislike/{userId}")
    public void dislike(@PathVariable final long reviewId,
                        @PathVariable final long userId) {
        reviewService.dislike(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/like/{userId}")
    public void deleteLike(@PathVariable final long reviewId,
                           @PathVariable final long userId) {
        reviewService.deleteLike(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/dislike/{userId}")
    public void deleteDislike(@PathVariable final long reviewId,
                              @PathVariable final long userId) {
        reviewService.deleteDislike(reviewId, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id) {
        reviewService.delete(id);
    }
}
