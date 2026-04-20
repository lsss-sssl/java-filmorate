package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dal.sql.FilmsSql;
import ru.yandex.practicum.filmorate.storage.dal.sql.GenreSql;
import ru.yandex.practicum.filmorate.storage.dal.sql.LikesSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.*;

@Repository
public class FilmRepository extends BaseRepository<Film> implements FilmStorage {
    private final NamedParameterJdbcTemplate namedJdbc;
    private final RowMapper<Film> filmRowMapper;

    public FilmRepository(
            JdbcTemplate jdbc,
            RowMapper<Film> mapper,
            SqlLoader sql,
            NamedParameterJdbcTemplate namedJdbc) {
        super(jdbc, mapper, sql);
        this.namedJdbc = namedJdbc;
        this.filmRowMapper = mapper;
    }

    @Override
    public List<Film> findAll() {
        List<Film> films = findMany(sql.load(FilmsSql.FIND_ALL));
        loadGenres(films);
        loadDirectors(films);
        return films;
    }

    @Override
    public Optional<Film> findById(long filmId) {
        Optional<Film> film = findOne(sql.load(FilmsSql.FIND_BY_ID), filmId);
        film.ifPresent(f -> f.setGenres(loadGenres(filmId)));
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
        resetDirectors(film);
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
        resetDirectors(film);
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
        return findPopular(count, null, null);
    }

    @Override
    public List<Film> findPopular(long count, Integer genreId, Integer year) {
        String sqlQuery = sql.load(FilmsSql.FIND_POPULAR_WITH_FILTERS);
        List<Film> films = jdbc.query(sqlQuery, filmRowMapper, genreId, genreId, year, year, count);
        loadGenres(films);
        loadDirectors(films);
        return films;
    }

    @Override
    public List<Film> findRecommendationsByUserId(long userId) {
        List<Film> films = findMany(
                sql.load(FilmsSql.FIND_RECOMMENDATIONS_BY_USER_ID),
                userId,
                userId,
                userId
        );
        loadGenres(films);
        loadDirectors(films);
        return films;
    }

    private void resetGenres(Film film) {
        jdbc.update(
                sql.load(GenreSql.DELETE_BY_ID),
                film.getId()
        );
        if (film.getGenres() == null || film.getGenres().isEmpty()) return;
        jdbc.batchUpdate(
                sql.load(GenreSql.CREATE),
                film.getGenres(),
                film.getGenres().size(),
                (ps, genre) -> {
                    ps.setLong(1, film.getId());
                    ps.setLong(2, genre.getId());
                }
        );
    }

    private Set<Genre> loadGenres(long filmId) {
        return new HashSet<>(jdbc.query(
                sql.load(FilmsSql.FIND_GENRES_BY_ID),
                (rs, rowNum) -> Genre.fromId(rs.getLong("genre_id")),
                filmId
        ));
    }

    private void loadGenres(List<Film> films) {
        if (films.isEmpty()) return;
        List<Long> filmIds = films.stream().map(Film::getId).toList();
        Map<Long, Set<Genre>> genresByFilmId = new HashMap<>();
        namedJdbc.query(
                sql.load(FilmsSql.FIND_GENRES_BY_FILM_IDS),
                new MapSqlParameterSource("filmIds", filmIds),
                rs -> {
            long filmId = rs.getLong("film_id");
            long genreId = rs.getLong("genre_id");
            genresByFilmId
                    .computeIfAbsent(filmId, id -> new LinkedHashSet<>())
                    .add(Genre.fromId(genreId));
        });
        for (Film film : films) {
            film.setGenres(genresByFilmId.getOrDefault(film.getId(), new LinkedHashSet<>()));
        }
    }

    private void loadDirectors(List<Film> films) {
        if (films.isEmpty()) return;

        List<Long> flmIds = films.stream().map(Film::getId).toList();
        Map<Long, Set<Director>> directorsByFilmId = new HashMap<>();
        namedJdbc.query(sql.load(FilmsSql.FIND_DIRECTORS_BY_FILM_IDS),
                new MapSqlParameterSource("filmIds", flmIds),
                rs -> {
                    long filmId = rs.getLong("film_id");

                    Director director = new Director();
                    director.setId(rs.getLong("director_id"));
                    director.setName(rs.getString("name"));

                    directorsByFilmId
                            .computeIfAbsent(filmId, id -> new LinkedHashSet<>())
                            .add(director);
                });
    }

    private void resetDirectors(Film film) {
        jdbc.update(sql.load(FilmsSql.DELETE_DIRECTORS_BY_FILM_ID), film.getId());
        if (film.getDirectors() == null || film.getDirectors().isEmpty()) return;;

        jdbc.batchUpdate(
                sql.load(FilmsSql.ADD_DIRECTOR_BY_FILM_IDS),
                film.getDirectors(),
                film.getDirectors().size(),
                (ps, director) -> {
                    ps.setLong(1, director.getId());
                    ps.setLong(2, film.getId());
                }
        );
    }

    @Override
    public void deleteById(long filmId) {
        update(sql.load(FilmsSql.DELETE_BY_ID), filmId);
    }
}