package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.dal.GenreRepository;
import ru.yandex.practicum.filmorate.storage.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@JdbcTest
@AutoConfigureTestDatabase
@Import({
        GenreRepository.class,
        GenreRowMapper.class,
        SqlLoader.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreRepositoryTest {
    private final GenreStorage genreRepository;

    @Test
    void shouldFindAllGenres() {
        List<Genre> genres = genreRepository.findAll();

        assertThat(genres).hasSize(6);
        assertThat(genres)
                .extracting(Genre::getId, Genre::getName)
                .containsExactly(
                        tuple(1L, "Комедия"),
                        tuple(2L, "Драма"),
                        tuple(3L, "Мультфильм"),
                        tuple(4L, "Триллер"),
                        tuple(5L, "Документальный"),
                        tuple(6L, "Боевик")
                );
    }

    @Test
    void shouldFindGenreById() {
        Optional<Genre> genre = genreRepository.findById(1);

        assertThat(genre)
                .isPresent()
                .hasValueSatisfying(foundGenre -> {
                    assertThat(foundGenre.getId()).isEqualTo(1L);
                    assertThat(foundGenre.getName()).isEqualTo("Комедия");
                });
    }
}