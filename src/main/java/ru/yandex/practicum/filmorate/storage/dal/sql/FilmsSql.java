package ru.yandex.practicum.filmorate.storage.dal.sql;

public final class FilmsSql {
    public static final String FIND_ALL = "sql/films/find_all_films.sql";
    public static final String FIND_BY_ID = "sql/films/find_film_by_id.sql";
    public static final String CREATE = "sql/films/create_film.sql";
    public static final String UPDATE = "sql/films/update_film.sql";
    public static final String DELETE_BY_ID = "sql/films/delete_film.sql";

    public static final String FIND_GENRES_BY_FILM_IDS = "sql/films/find_genres_by_film_ids.sql";
    public static final String FIND_DIRECTORS_BY_FILM_IDS = "sql/films/find_directors_by_film_ids.sql";

    public static final String FIND_POPULAR_WITH_FILTERS = "sql/films/find_popular_with_filters.sql";
    public static final String FIND_RECOMMENDATIONS_BY_USER_ID = "sql/films/find_recommendations_by_user_id.sql";

    public static final String FIND_BY_DIRECTOR_ID_ORDER_BY_YEAR = "sql/films/find_by_director_id_order_by_year.sql";
    public static final String FIND_BY_DIRECTOR_ID_ORDER_BY_LIKES = "sql/films/find_by_director_id_order_by_likes.sql";

    public static final String SEARCH_FILMS = "sql/films/search_films.sql";
}