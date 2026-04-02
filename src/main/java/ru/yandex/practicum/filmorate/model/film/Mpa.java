package ru.yandex.practicum.filmorate.model.film;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Mpa {
    G(1, "G"),
    PG(2, "PG"),
    PG_13(3, "PG-13"),
    R(4, "R"),
    NC_17(5, "NC-17");

    private final long id;
    private final String name;

    public static Mpa fromId(long id) {
        for (Mpa mpa : values()) {
            if (mpa.getId() == id) {
                return mpa;
            }
        }
        throw new NotFoundException("Mpa not found by id=" + id);
    }

    @JsonCreator
    public static Mpa fromJson(Map<String, Object> json) {
        if (json == null || !json.containsKey("id")) {
            throw new IllegalArgumentException("MPA id is required");
        }

        long id = ((Number) json.get("id")).longValue();
        return fromId(id);
    }

    @JsonValue
    public Map<String, Long> toJson() {
        return Map.of("id", id);
    }
}