package ru.yandex.practicum.filmorate.model.film;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Genre {
    COMEDY(1, "Комедия"),
    DRAMA(2, "Драма"),
    CARTOON(3, "Мультфильм"),
    THRILLER(4, "Триллер"),
    DOCUMENTARY(5, "Документальный"),
    ACTION(6, "Боевик");

    private final long id;
    private final String name;

    public static Genre fromId(long id) {
        for (Genre genre : values()) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        throw new NotFoundException("Genre not found by id=" + id);
    }

    @JsonCreator
    public static Genre fromJson(Map<String, Object> json) {
        if (json == null || !json.containsKey("id")) {
            throw new IllegalArgumentException("Genre id is required");
        }

        int id = ((Number) json.get("id")).intValue();
        return fromId(id);
    }

    @JsonValue
    public Map<String, Long> toJson() {
        return Map.of("id", id);
    }
}