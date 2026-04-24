package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.review.NewReviewRequest;
import ru.yandex.practicum.filmorate.dto.review.ReviewDto;
import ru.yandex.practicum.filmorate.dto.review.UpdateReviewRequest;
import ru.yandex.practicum.filmorate.model.Review;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReviewMapper {
    public static Review mapToReview(NewReviewRequest request) {
        return Review.builder()
                .content(request.getContent())
                .positive(request.getPositive())
                .userId(request.getUserId())
                .filmId(request.getFilmId())
                .useful(0)
                .build();
    }

    public static ReviewDto mapToReviewDto(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .positive(review.isPositive())
                .userId(review.getUserId())
                .filmId(review.getFilmId())
                .useful(review.getUseful())
                .build();
    }

    public static void updateReviewFields(Review review, UpdateReviewRequest request) {
        review.setContent(request.getContent());
        review.setPositive(request.getPositive());
    }
}
