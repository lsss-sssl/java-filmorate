package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;
import ru.yandex.practicum.filmorate.storage.dal.sql.DirectorSql;
import ru.yandex.practicum.filmorate.storage.dal.sql.FilmsSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.*;

@Repository
public  class DirectorRepository extends BaseRepository<Director> implements DirectorStorage {
    private final NamedParameterJdbcTemplate namedJdbc;
    private final RowMapper<Director> mapper;
    private final RowMapper<Film> filmRowMapper;

    public DirectorRepository(JdbcTemplate jdbc,
                              RowMapper<Director> mapper,
                              SqlLoader sql,
                              NamedParameterJdbcTemplate namedJdbc,
                              RowMapper<Film> filmRowMapper) {
        super(jdbc, mapper, sql);
        this.namedJdbc = namedJdbc;
        this.mapper = mapper;
        this.filmRowMapper = filmRowMapper;
    }

    @Override
    public List<Director> findAll() {
        List<Director> directors = findMany(sql.load(DirectorSql.FIND_ALL));
        return directors;
    }

    @Override
    public Optional<Director> findById(long directorId) {
        return findOne(sql.load(DirectorSql.FIND_BY_ID), directorId);
    }

    @Override
    public Director save(Director director) {
        long id = insert(sql.load(DirectorSql.CREATE),
                director.getName());
        director.setId(id);
        return director;
    }

    @Override
    public Director update(Director director) {
        update(sql.load(FilmsSql.UPDATE),
                director.getId(),
                director.getName());
        return director;
    }

    @Override
    public List<Film> findFilmsSorted(Long directorId, String sortBy) {
        String sqlQuery = sql.load(DirectorSql.FIND_FILMS_BY_DIRECTOR_IDS);
        List<Film> films = jdbc.query(sqlQuery, filmRowMapper, directorId, sortBy, sortBy);
        return films;
    }

    @Override
    public void deleteById(long directorId) {
        update(sql.load(DirectorSql.DELETE_BY_ID), directorId);
    }
}