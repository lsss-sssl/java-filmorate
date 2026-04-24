package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;
import ru.yandex.practicum.filmorate.storage.dal.sql.DirectorSql;
import ru.yandex.practicum.filmorate.util.SqlLoader;

import java.util.List;
import java.util.Optional;

@Repository
public class DirectorRepository extends BaseRepository<Director> implements DirectorStorage {

    public DirectorRepository(JdbcTemplate jdbc,
                              RowMapper<Director> mapper,
                              SqlLoader sql) {
        super(jdbc, mapper, sql);
    }

    @Override
    public List<Director> findAll() {
        return findMany(sql.load(DirectorSql.FIND_ALL));
    }

    @Override
    public Optional<Director> findById(long directorId) {
        return findOne(
                sql.load(DirectorSql.FIND_BY_ID),
                directorId);
    }

    @Override
    public Director save(Director director) {
        long id = insert(
                sql.load(DirectorSql.CREATE),
                director.getName()
        );
        director.setId(id);
        return director;
    }

    @Override
    public Director update(Director director) {
        update(
                sql.load(DirectorSql.UPDATE),
                director.getName(),
                director.getId()
        );
        return director;
    }

    @Override
    public void deleteById(long directorId) {
        update(
                sql.load(DirectorSql.DELETE_BY_ID),
                directorId);
    }
}