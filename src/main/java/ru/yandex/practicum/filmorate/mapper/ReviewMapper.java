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
        Review review = new Review();
        review.setContent(request.getContent());
        review.setPositive(request.isPositive());
        review.setUserId(request.getUserId());
        review.setFilmId(request.getFilmId());
        review.setUseful(0);
        return review;
    }

    public static ReviewDto mapToReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setPositive(review.isPositive());
        dto.setUserId(review.getUserId());
        dto.setFilmId(review.getFilmId());
        dto.setUseful(review.getUseful());
        return dto;
    }

    public static void updateReviewFields(Review review, UpdateReviewRequest request) {
        review.setContent(request.getContent());
        review.setPositive(request.isPositive());
        review.setUseful(request.getUserId());
        review.setFilmId(request.getFilmId());
    }
}
