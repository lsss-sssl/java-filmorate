SELECT film_id, genre_id
FROM film_genres
WHERE film_id IN (:filmIds)
ORDER BY film_id, genre_id