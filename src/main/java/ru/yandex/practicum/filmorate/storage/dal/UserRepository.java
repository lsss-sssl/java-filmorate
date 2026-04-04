package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.dal.sql.FriendsSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;
import ru.yandex.practicum.filmorate.storage.dal.sql.UsersSql;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> implements UserStorage {

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper, SqlLoader sql) {
        super(jdbc, mapper, sql);
    }

    @Override
    public List<User> findAll() {
        return findMany(sql.load(UsersSql.FIND_ALL));
    }

    @Override
    public Optional<User> findById(long userId) {
        return findOne(
                sql.load(UsersSql.FIND_BY_ID),
                userId
        );
    }

    @Override
    public List<User> findFriendsById(long userId) {
        return findMany(
                sql.load(FriendsSql.FIND_FRIENDS_BY_ID),
                userId
        );
    }

    @Override
    public List<User> findCommonFriendsById(long userId, final long friendId) {
        return findMany(
                sql.load(FriendsSql.FIND_COMMON_FRIENDS),
                userId,
                friendId
        );
    }

    @Override
    public User save(User user) {
        long id = insert(
                sql.load(UsersSql.CREATE),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        update(
                sql.load(UsersSql.UPDATE),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    @Override
    public void classifyFriendship(long userId, long friendId, FriendshipStatus status) {
        update(
                sql.load(FriendsSql.CREATE_FRIENDSHIP),
                userId,
                friendId,
                status.getId()
        );
    }

    @Override
    public void endFriendship(long userId, long friendId) {
        update(
                sql.load(FriendsSql.DELETE_FRIENDSHIP),
                userId,
                friendId
        );
    }

    @Override
    public Optional<Long> findFriendshipStatus(long userId, long friendId) {
        return jdbc.query(
                sql.load(FriendsSql.FIND_FRIENDSHIP_STATUS),
                (rs, rowNum) -> rs.getLong("status_id"),
                userId,
                friendId
        ).stream().findFirst();
    }
}