package ru.yandex.practicum.filmorate.storage.dal.sql;

public class DirectorSql {
    public static final String FIND_ALL = "sql/directors/find_all_director.sql";
    public static final String FIND_BY_ID = "sql/directors/find_director_by_id.sql";
    public static final String CREATE = "sql/directors/create_director.sql";
    public static final String UPDATE = "sql/directors/update_director.sql";
    public static final String FIND_POPULAR_WITH_FILTERS = "sql/directors_vis_films/find_popular_with_filters.sql";
    public static final String DELETE_BY_ID = "sql/directors/delete_director.sql";
    public static final String FIND_FILMS_BY_DIRECTOR_IDS = "sql/directors_vis_films/find_films_by_director_ids/sql";
    public static final String DELETE_FILMS_BY_DIRECTOR_ID = "sql/directors_vis_films/delete_films_by_director_id.sql";
    public static final String AddFilmByDirectorId = "sql/directors_vis_films/add_film_for_director.sql";
}
