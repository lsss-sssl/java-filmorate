package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.dal.MpaRepository;
import ru.yandex.practicum.filmorate.storage.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@JdbcTest
@AutoConfigureTestDatabase
@Import({
        MpaRepository.class,
        MpaRowMapper.class,
        SqlLoader.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaRepositoryTest {
    private final MpaStorage mpaRepository;

    @Test
    void shouldFindAllMpa() {
        List<Mpa> mpas = mpaRepository.findAll();

        assertThat(mpas).hasSize(5);
        assertThat(mpas)
                .extracting(Mpa::getId, Mpa::getName)
                .containsExactly(
                        tuple(1L, "G"),
                        tuple(2L, "PG"),
                        tuple(3L, "PG-13"),
                        tuple(4L, "R"),
                        tuple(5L, "NC-17")
                );
    }

    @Test
    void shouldFindMpaById() {
        Optional<Mpa> mpa = mpaRepository.findById(1);

        assertThat(mpa)
                .isPresent()
                .hasValueSatisfying(foundMpa -> {
                    assertThat(foundMpa.getId()).isEqualTo(1L);
                    assertThat(foundMpa.getName()).isEqualTo("G");
                });
    }
}