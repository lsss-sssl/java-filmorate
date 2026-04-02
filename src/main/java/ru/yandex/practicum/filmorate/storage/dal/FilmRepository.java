package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dal.sql.FilmsSql;
import ru.yandex.practicum.filmorate.storage.dal.sql.GenreSql;
import ru.yandex.practicum.filmorate.storage.dal.sql.LikesSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class FilmRepository extends BaseRepository<Film> implements FilmStorage {

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper, SqlLoader sql) {
        super(jdbc, mapper, sql);
    }

    @Override
    public List<Film> findAll() {
        List<Film> films = findMany(sql.load(FilmsSql.FIND_ALL));
        for (Film film : films) {
            film.setGenres(getGenres(film.getId()));
        }
        return films;
    }

    @Override
    public Optional<Film> findById(long filmId) {
        Optional<Film> film = findOne(sql.load(FilmsSql.FIND_BY_ID), filmId);
        film.ifPresent(f -> f.setGenres(getGenres(f.getId())));
        return film;
    }

    @Override
    public Film save(Film film) {
        long id = insert(
                sql.load(FilmsSql.CREATE),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId()
        );
        film.setId(id);
        resetGenres(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        update(
                sql.load(FilmsSql.UPDATE),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );
        resetGenres(film);
        return film;
    }

    @Override
    public void addLike(long filmId, long userId) {
        update(
                sql.load(LikesSql.ADD),
                filmId,
                userId
        );
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        update(
                sql.load(LikesSql.DELETE),
                filmId,
                userId
        );
    }

    @Override
    public List<Film> findPopular(long count) {
        List<Film> films = findMany(sql.load(FilmsSql.FIND_POPULAR), count);
        for (Film film : films) {
            film.setGenres(getGenres(film.getId()));
        }
        return films;
    }

    private void resetGenres(Film film) {
        jdbc.update(
                sql.load(GenreSql.DELETE_BY_ID),
                film.getId()
        );
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            return;
        }
        for (Genre genre : film.getGenres()) {
            update(sql.load(GenreSql.CREATE), film.getId(), genre.getId());
        }
    }

    private Set<Genre> getGenres(long filmId) {
        return new HashSet<>(jdbc.query(
                sql.load(FilmsSql.FIND_GENRES_BY_ID),
                (rs, rowNum) -> Genre.fromId(rs.getLong("genre_id")),
                filmId
        ));
    }
}