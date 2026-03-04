package ru.yandex.practicum.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class FilmorateUserTests {

    private Validator validator;
    private User user;

    @BeforeEach
    void update() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        user = new User();
        user.setEmail("user@mail.com");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(2000, 1, 1));

    }

    @Test
    void shouldFail_whenEmailIsBlank() {
        user.setEmail("");
        assertThat(validator.validate(user)).isNotEmpty();
    }

    @Test
    void shouldFail_whenEmailIsInvalid() {
        user.setEmail("meow-meow");
        assertThat(validator.validate(user)).isNotEmpty();
    }

    @Test
    void shouldFail_whenLoginContainsSpaces() {
        user.setLogin("meow meow");
        assertThat(validator.validate(user)).isNotEmpty();
    }

    @Test
    void shouldFail_whenLoginIsNull() {
        user.setLogin(null);
        assertThat(validator.validate(user)).isNotEmpty();
    }

    @Test
    void shouldFail_whenBirthdayIsInFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));
        assertThat(validator.validate(user)).isNotEmpty();
    }

    @Test
    void shouldPass_WhenValidUser() {
        assertThat(validator.validate(user)).isEmpty();
    }
}

