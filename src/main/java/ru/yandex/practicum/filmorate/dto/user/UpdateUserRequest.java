package ru.yandex.practicum.filmorate.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.nospace.NoSpaces;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private long id;
    @Email(message = "Email должен быть корректным")
    @NotBlank(message = "Email не может быть пустым")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    @NoSpaces(message = "Логин не может содержать пробелы")
    private String login;
    private String name;
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    public boolean hasEmail() {
        return !(email == null || email.isBlank());
    }

    public boolean hasLogin() {
        return !(login == null || login.isBlank());
    }

    public boolean hasName() {
        return !(name == null || name.isBlank());
    }

    public boolean hasBirthday() {
        return !(birthday == null);
    }
}
