package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.EventStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.dal.EventRepository;
import ru.yandex.practicum.filmorate.storage.dal.UserRepository;
import ru.yandex.practicum.filmorate.storage.dal.mappers.EventRowMapper;
import ru.yandex.practicum.filmorate.storage.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import({
        EventRepository.class,
        EventRowMapper.class,
        UserRepository.class,
        UserRowMapper.class,
        SqlLoader.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventRepositoryTest {
    private final EventStorage eventRepository;
    private final UserStorage userRepository;

    private User makeUser(String email, String login) {
        return User.builder()
                .email(email)
                .login(login)
                .name(login)
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
    }

    private Event makeEvent(long timestamp,
                            long userId,
                            String eventType,
                            String operation,
                            long entityId) {
        return Event.builder()
                .timestamp(timestamp)
                .userId(userId)
                .eventType(eventType)
                .operation(operation)
                .entityId(entityId)
                .build();
    }

    @Test
    void shouldSaveAndFindFeedByUserId() {
        User user = userRepository.save(makeUser("feed1@example.com", "feed1"));

        Event e1 = makeEvent(1000L, user.getId(), "LIKE", "ADD", 10L);
        Event e2 = makeEvent(2000L, user.getId(), "FRIEND", "ADD", 20L);

        eventRepository.save(e1);
        eventRepository.save(e2);

        List<Event> feed = eventRepository.findFeedByUserId(user.getId());

        assertThat(feed.size()).isEqualTo(2);

        assertThat(feed.getFirst().getEventId()).isEqualTo(e1.getEventId());
        assertThat(feed.getFirst().getUserId()).isEqualTo(user.getId());
        assertThat(feed.getFirst().getEventType()).isEqualTo("LIKE");
        assertThat(feed.getFirst().getOperation()).isEqualTo("ADD");
        assertThat(feed.getFirst().getEntityId()).isEqualTo(10L);

        assertThat(feed.get(1).getEventId()).isEqualTo(e2.getEventId());
        assertThat(feed.get(1).getUserId()).isEqualTo(user.getId());
        assertThat(feed.get(1).getEventType()).isEqualTo("FRIEND");
        assertThat(feed.get(1).getOperation()).isEqualTo("ADD");
        assertThat(feed.get(1).getEntityId()).isEqualTo(20L);
    }
}