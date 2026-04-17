package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.storage.EventStorage;
import ru.yandex.practicum.filmorate.storage.dal.sql.EventsSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class EventRepository extends BaseRepository<Event> implements EventStorage {
    public EventRepository(JdbcTemplate jdbc, RowMapper<Event> mapper, SqlLoader sql) {
        super(jdbc, mapper, sql);
    }

    @Override
    public void save(Event event) {
        long id = insert(
                sql.load(EventsSql.CREATE),
                new Timestamp(event.getTimestamp()),
                event.getEventType(),
                event.getOperation(),
                event.getUserId(),
                event.getEntityId()
        );
        event.setEventId(id);
    }

    @Override
    public List<Event> findFeedByUserId(long userId) {
        return findMany(sql.load(EventsSql.FIND_FEED_BY_USER_ID), userId);
    }
}