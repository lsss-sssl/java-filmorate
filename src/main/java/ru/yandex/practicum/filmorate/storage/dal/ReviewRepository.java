package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.List;
import java.util.Optional;

public class ReviewRepository extends BaseRepository<Review> implements ReviewStorage {
    private final NamedParameterJdbcTemplate namedJdbc;

    public ReviewRepository(
            JdbcTemplate jdbc,
            RowMapper<Review> mapper,
            SqlLoader sql,
            NamedParameterJdbcTemplate namedJdbc) {
        super(jdbc, mapper, sql);
        this.namedJdbc = namedJdbc;
    }

    @Override
    public List<Review> findAll(long count) {
        return List.of();
    }

    @Override
    public Optional<Review> findById(long reviewId) {
        return Optional.empty();
    }

    @Override
    public List<Review> findAllByFilmId(long reviewId, long count) {
        return List.of();
    }

    @Override
    public Review save(Review review) {
        return null;
    }

    @Override
    public Review update(Review review) {
        return null;
    }

    @Override
    public void addLike(long reviewId, long userId) {

    }

    @Override
    public void deleteLike(long reviewId, long userId) {

    }

    @Override
    public void addDislike(long reviewId, long userId) {

    }

    @Override
    public void deleteDislike(long reviewId, long userId) {

    }

    @Override
    public void delete(long reviewId) {

    }
}
