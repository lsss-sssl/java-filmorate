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
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ReviewDto create(@Valid @RequestBody NewReviewRequest request) {
        return reviewService.create(request);
    }

    @PutMapping
    public ReviewDto update(@Valid @RequestBody UpdateReviewRequest request) {
        return reviewService.update(request);
    }

    @GetMapping("/{id}")
    public ReviewDto getById(@PathVariable long id) {
        return reviewService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reviewService.delete(id);
    }

    @GetMapping
    public List<ReviewDto> get(@RequestParam(required = false) Long filmId,
                               @RequestParam(defaultValue = "10") int count) {
        if (filmId != null) {
            return reviewService.getByFilmId(filmId, count);
        }
        return reviewService.getAll(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public ReviewDto addLike(@PathVariable long id, @PathVariable long userId) {
        return reviewService.addLike(id, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public ReviewDto addDislike(@PathVariable long id, @PathVariable long userId) {
        return reviewService.addDislike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ReviewDto removeLike(@PathVariable long id, @PathVariable long userId) {
        return reviewService.removeLike(id, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public ReviewDto removeDislike(@PathVariable long id, @PathVariable long userId) {
        return reviewService.removeDislike(id, userId);
    }
}