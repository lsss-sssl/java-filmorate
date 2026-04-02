package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.util.GenreSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository extends BaseRepository<Genre> implements GenreStorage {

    public GenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper, SqlLoader sql) {
        super(jdbc, mapper, sql);
    }

    @Override
    public List<Genre> findAll() {
        return findMany(sql.load(GenreSql.FIND_ALL));
    }

    @Override
    public Optional<Genre> findById(long genreId) {
        return findOne(
                sql.load(GenreSql.FIND_BY_ID),
                genreId
        );
    }
}
