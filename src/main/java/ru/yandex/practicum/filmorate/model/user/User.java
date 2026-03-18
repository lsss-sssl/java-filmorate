package ru.yandex.practicum.filmorate.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.NoSpaces;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private Long id;

    @Email(message = "Email должен быть корректным")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @NotBlank(message = "Логин не может быть пустым")
    @NoSpaces(message = "Логин не может содержать пробелы")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();

    private Set<Long> confirmedFriends = new HashSet<>();

    private Set<Long> unconfimedFriends = new HashSet<>();
}
