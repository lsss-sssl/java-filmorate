package ru.yandex.practicum.filmorate.storage.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public final class EventRowMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Timestamp ts = rs.getTimestamp("timestamp");
        return Event.builder()
                .eventId(rs.getLong("id"))
                .timestamp(ts != null ? ts.getTime() : null)
                .userId(rs.getLong("user_id"))
                .eventType(rs.getString("event_type"))
                .operation(rs.getString("operation"))
                .entityId(rs.getLong("entity_id"))
                .build();
    }
}