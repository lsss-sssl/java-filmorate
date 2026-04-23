package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.dal.sql.ReviewsSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepository extends BaseRepository<Review> implements ReviewStorage {

    public ReviewRepository(
            JdbcTemplate jdbc,
            RowMapper<Review> mapper,
            SqlLoader sql) {
        super(jdbc, mapper, sql);
    }

    @Override
    public List<Review> findAll(long count) {
        return findMany(
                sql.load(ReviewsSql.FIND_ALL),
                count
        );
    }

    @Override
    public Optional<Review> findById(long reviewId) {
        return findOne(
                sql.load(ReviewsSql.FIND_BY_ID),
                reviewId
        );
    }

    @Override
    public List<Review> findAllByFilmId(long filmId, long count) {
        return findMany(
                sql.load(ReviewsSql.FIND_ALL_BY_FILM_ID),
                filmId,
                count
        );
    }

    @Override
    public Review save(Review review) {
        long id = insert(
                sql.load(ReviewsSql.CREATE),
                review.getContent(),
                review.isPositive(),
                review.getUserId(),
                review.getFilmId()
        );
        review.setId(id);
        review.setUseful(0);
        return review;
    }

    @Override
    public Review update(Review review) {
        update(
                sql.load(ReviewsSql.UPDATE),
                review.getContent(),
                review.isPositive(),
                review.getId()
        );
        return review;
    }

    @Override
    public void delete(long reviewId) {
        delete(
                sql.load(ReviewsSql.DELETE),
                reviewId
        );
    }
}