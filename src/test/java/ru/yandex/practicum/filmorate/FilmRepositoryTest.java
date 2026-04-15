package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dal.FilmRepository;
import ru.yandex.practicum.filmorate.storage.dal.UserRepository;
import ru.yandex.practicum.filmorate.storage.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.storage.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase
@Import({
        FilmRepository.class,
        UserRepository.class,
        FilmRowMapper.class,
        UserRowMapper.class,
        SqlLoader.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmRepositoryTest {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    private Film makeFilm(String name, Mpa mpa, Set<Genre> genres) {
        Film film = new Film();
        film.setName(name);
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);
        film.setMpa(mpa);
        film.setGenres(genres);
        return film;
    }

    private User makeUser(String email, String login) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setName(login);
        user.setBirthday(LocalDate.of(2000, 1, 1));
        return user;
    }

    @Test
    void shouldFindAllFilms() {
        filmRepository.save(makeFilm("Film 1", Mpa.G, Set.of()));
        filmRepository.save(makeFilm("Film 2", Mpa.PG, Set.of(Genre.THRILLER)));
        List<Film> films = filmRepository.findAll();
        assertTrue(films.size() >= 2);
    }

    @Test
    void shouldFindFilmById() {
        Film savedFilm = filmRepository.save(makeFilm("Film 3", Mpa.R, Set.of(Genre.ACTION)));
        Optional<Film> foundFilm = filmRepository.findById(savedFilm.getId());
        assertThat(foundFilm)
                .isPresent()
                .hasValueSatisfying(film -> {
                    assertThat(film.getId()).isEqualTo(savedFilm.getId());
                    assertThat(film.getName()).isEqualTo("Film 3");
                    assertThat(film.getMpa()).isEqualTo(Mpa.R);
                    assertTrue(film.getGenres().stream()
                            .map(Genre::getId)
                            .toList()
                            .contains(6L));
                });
    }

    @Test
    void shouldSaveFilm() {
        Film film = makeFilm("Film 4", Mpa.PG_13, Set.of(Genre.COMEDY, Genre.DRAMA));
        Film savedFilm = filmRepository.save(film);
        assertThat(savedFilm.getId()).isPositive();
        assertThat(savedFilm.getName()).isEqualTo("Film 4");
        assertThat(savedFilm.getMpa()).isEqualTo(Mpa.PG_13);
        assertTrue(savedFilm.getGenres().stream()
                .map(Genre::getId)
                .toList()
                .containsAll(List.of(1L, 2L))
        );
    }

    @Test
    void shouldAddAndDeleteLike() {
        User user = userRepository.save(makeUser("film-like-user@example.com", "filmLikeUser"));
        Film film = filmRepository.save(makeFilm("Film 5", Mpa.PG, Set.of()));
        filmRepository.addLike(film.getId(), user.getId());
        List<Film> popularAfterLike = filmRepository.findPopular(10);
        assertThat(popularAfterLike.getFirst().getId()).isEqualTo(film.getId());
        filmRepository.deleteLike(film.getId(), user.getId());
        List<Film> popularAfterDelete = filmRepository.findPopular(10);
        assertThat(popularAfterDelete.stream().filter(f -> f.getId() == (film.getId())).findFirst()).isPresent();
    }

    @Test
    void shouldFindPopularFilmsOrderedByLikes() {
        User user1 = userRepository.save(makeUser("popular1@example.com", "popular1"));
        User user2 = userRepository.save(makeUser("popular2@example.com", "popular2"));
        User user3 = userRepository.save(makeUser("popular3@example.com", "popular3"));
        Film film1 = filmRepository.save(makeFilm("Popular 1", Mpa.G, Set.of()));
        Film film2 = filmRepository.save(makeFilm("Popular 2", Mpa.G, Set.of()));
        Film film3 = filmRepository.save(makeFilm("Popular 3", Mpa.G, Set.of()));
        filmRepository.addLike(film2.getId(), user1.getId());
        filmRepository.addLike(film2.getId(), user2.getId());
        filmRepository.addLike(film2.getId(), user3.getId());
        filmRepository.addLike(film3.getId(), user1.getId());
        filmRepository.addLike(film3.getId(), user2.getId());
        filmRepository.addLike(film1.getId(), user1.getId());
        List<Film> popularFilms = filmRepository.findPopular(10);
        assertTrue(popularFilms.stream()
                .map(Film::getId)
                .toList()
                .containsAll(List.of(film2.getId(), film3.getId(), film1.getId()))
        );
    }
}