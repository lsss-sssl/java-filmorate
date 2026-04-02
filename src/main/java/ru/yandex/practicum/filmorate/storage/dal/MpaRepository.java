package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.util.MpaSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.List;
import java.util.Optional;

@Repository
public class MpaRepository extends BaseRepository<Mpa> implements MpaStorage {

    public MpaRepository(JdbcTemplate jdbc, RowMapper<Mpa> mapper, SqlLoader sql) {
        super(jdbc, mapper, sql);
    }

    @Override
    public List<Mpa> findAll() {
        return findMany(sql.load(MpaSql.FIND_ALL));
    }

    @Override
    public Optional<Mpa> findById(long mpaId) {
        return findOne(
                sql.load(MpaSql.FIND_BY_ID),
                mpaId
        );
    }
}