package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.ReviewReactionStorage;
import ru.yandex.practicum.filmorate.storage.dal.sql.ReviewReactionsSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.Optional;

@Component
public class ReviewReactionRepository implements ReviewReactionStorage {
    private final JdbcTemplate jdbc;
    private final SqlLoader sql;
    private final RowMapper<Boolean> reactionRowMapper = (rs, rowNum) -> rs.getBoolean("is_like");

    public ReviewReactionRepository(JdbcTemplate jdbc, SqlLoader sql) {
        this.jdbc = jdbc;
        this.sql = sql;
    }

    @Override
    public Optional<Boolean> findReaction(long reviewId, long userId) {
        return jdbc.query(
                sql.load(ReviewReactionsSql.FIND_REACTION),
                reactionRowMapper,
                reviewId,
                userId
        ).stream().findFirst();
    }

    @Override
    public void addReaction(long reviewId, long userId, boolean isLike) {
        jdbc.update(
                sql.load(ReviewReactionsSql.ADD_REACTION),
                reviewId,
                userId,
                isLike
        );
    }

    @Override
    public void updateReaction(long reviewId, long userId, boolean isLike) {
        jdbc.update(
                sql.load(ReviewReactionsSql.UPDATE_REACTION),
                isLike,
                reviewId,
                userId
        );
    }

    @Override
    public void deleteReaction(long reviewId, long userId, boolean isLike) {
        jdbc.update(
                sql.load(ReviewReactionsSql.DELETE_REACTION),
                reviewId,
                userId,
                isLike
        );
    }
}