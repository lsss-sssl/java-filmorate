SELECT f.*
FROM films f
         JOIN films_directors fd ON f.id = fd.film_id
WHERE fd.director_id = ?
ORDER BY f.release_date, f.id