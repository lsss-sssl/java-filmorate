package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.dal.UserRepository;
import ru.yandex.practicum.filmorate.storage.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase
@Import({
        UserRepository.class,
        UserRowMapper.class,
        SqlLoader.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserRepositoryTest {
    private final UserStorage userRepository;

    private User makeUser(String email, String login) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(login);
        user.setBirthday(LocalDate.of(2000, 1, 1));
        return user;
    }

    @Test
    void shouldFindAllUsers() {
        userRepository.save(makeUser("user1@example.com", "user1"));
        userRepository.save(makeUser("user2@example.com", "user2"));
        List<User> users = userRepository.findAll();
        assertTrue(users.size() >= 2);
    }

    @Test
    void shouldFindUserById() {
        User savedUser = userRepository.save(makeUser("user3@example.com", "user3"));
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertThat(foundUser)
                .isPresent()
                .hasValueSatisfying(user -> {
                    assertThat(user.getId()).isEqualTo(savedUser.getId());
                    assertThat(user.getEmail()).isEqualTo("user3@example.com");
                });
    }

    @Test
    void shouldFindFriendsById() {
        User user = userRepository.save(makeUser("user4@example.com", "user4"));
        User friend = userRepository.save(makeUser("user5@example.com", "user5"));
        userRepository.classifyFriendship(user.getId(), friend.getId(), FriendshipStatus.UNCONFIRMED);
        List<User> friends = userRepository.findFriendsById(user.getId());
        assertTrue(friends.stream()
                .map(User::getId)
                .toList()
                .contains(friend.getId())
        );
    }

    @Test
    void shouldFindCommonFriends() {
        User user1 = userRepository.save(makeUser("user6@example.com", "user6"));
        User user2 = userRepository.save(makeUser("user7@example.com", "user7"));
        User commonFriend = userRepository.save(makeUser("user8@example.com", "user8"));
        userRepository.classifyFriendship(user1.getId(), commonFriend.getId(), FriendshipStatus.UNCONFIRMED);
        userRepository.classifyFriendship(user2.getId(), commonFriend.getId(), FriendshipStatus.UNCONFIRMED);
        List<User> commonFriends = userRepository.findCommonFriendsById(user1.getId(), user2.getId());
        assertTrue(commonFriends.stream()
                .map(User::getId)
                .toList()
                .contains(commonFriend.getId())
        );
    }

    @Test
    void shouldSaveUser() {
        User user = makeUser("user9@example.com", "user9");
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isPositive();
        assertThat(savedUser.getEmail()).isEqualTo("user9@example.com");
        assertThat(savedUser.getLogin()).isEqualTo("user9");
    }

    @Test
    void shouldUpdateUser() {
        User savedUser = userRepository.save(makeUser("use10@example.com", "user10"));
        savedUser.setEmail("updated@example.com");
        savedUser.setLogin("updatedLogin");
        savedUser.setName("updatedName");
        User updatedUser = userRepository.update(savedUser);
        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
        assertThat(updatedUser.getLogin()).isEqualTo("updatedLogin");
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertThat(foundUser)
                .isPresent()
                .hasValueSatisfying(user -> {
                    assertThat(user.getEmail()).isEqualTo("updated@example.com");
                    assertThat(user.getLogin()).isEqualTo("updatedLogin");
                    assertThat(user.getName()).isEqualTo("updatedName");
                });
    }

    @Test
    void shouldClassifyFriendship() {
        User user = userRepository.save(makeUser("user11@example.com", "user11"));
        User friend = userRepository.save(makeUser("user12@example.com", "user12"));
        userRepository.classifyFriendship(user.getId(), friend.getId(), FriendshipStatus.UNCONFIRMED);
        Optional<Long> status = userRepository.findFriendshipStatus(user.getId(), friend.getId());
        assertThat(status).isPresent();
        assertThat(status).hasValue(FriendshipStatus.UNCONFIRMED.getId());
    }

    @Test
    void shouldEndFriendship() {
        User user = userRepository.save(makeUser("user13@example.com", "user13"));
        User friend = userRepository.save(makeUser("user14@example.com", "user14"));
        userRepository.classifyFriendship(user.getId(), friend.getId(), FriendshipStatus.UNCONFIRMED);
        userRepository.endFriendship(user.getId(), friend.getId());
        Optional<Long> status = userRepository.findFriendshipStatus(user.getId(), friend.getId());
        assertThat(status).isEmpty();
    }
}