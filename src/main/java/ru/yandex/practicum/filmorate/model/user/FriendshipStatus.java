package ru.yandex.practicum.filmorate.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FriendshipStatus {
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
}
