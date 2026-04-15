package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.dal.sql.ReviewReactionsSql;
import ru.yandex.practicum.filmorate.storage.dal.sql.ReviewsSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepository extends BaseRepository<Review> implements ReviewStorage {
    public ReviewRepository(JdbcTemplate jdbc, RowMapper<Review> mapper, SqlLoader sql) {
        super(jdbc, mapper, sql);
    }

    @Override
    public Review save(Review review) {
        long id = insert(
                sql.load(ReviewsSql.CREATE),
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId()
        );
        review.setReviewId(id);
        return findById(id).orElse(review);
    }

    @Override
    public Review update(Review review) {
        update(
                sql.load(ReviewsSql.UPDATE),
                review.getContent(),
                review.getIsPositive(),
                review.getReviewId()
        );
        return findById(review.getReviewId()).orElse(review);
    }

    @Override
    public Optional<Review> findById(long reviewId) {
        return findOne(sql.load(ReviewsSql.FIND_BY_ID), reviewId);
    }

    @Override
    public void delete(long reviewId) {
        update(sql.load(ReviewsSql.DELETE_BY_ID), reviewId);
    }

    @Override
    public List<Review> findByFilmId(long filmId, int count) {
        return findMany(sql.load(ReviewsSql.FIND_BY_FILM_ID), filmId, count);
    }

    @Override
    public List<Review> findAll(int count) {
        return findMany(sql.load(ReviewsSql.FIND_ALL), count);
    }

    @Override
    public Optional<Boolean> findReaction(long reviewId, long userId) {
        try {
            Boolean result = jdbc.queryForObject(sql.load(ReviewReactionsSql.FIND_REACTION), Boolean.class, reviewId, userId);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void addReaction(long reviewId, long userId, boolean isLike) {
        update(sql.load(ReviewReactionsSql.ADD_REACTION), reviewId, userId, isLike);
    }

    @Override
    public void updateReaction(long reviewId, long userId, boolean isLike) {
        update(sql.load(ReviewReactionsSql.UPDATE_REACTION), isLike, reviewId, userId);
    }

    @Override
    public void deleteReaction(long reviewId, long userId) {
        update(sql.load(ReviewReactionsSql.DELETE_REACTION), reviewId, userId);
    }
}