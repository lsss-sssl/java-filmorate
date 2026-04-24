SELECT f.*
FROM films f
         JOIN films_directors fd ON f.id = fd.film_id
         LEFT JOIN film_likes fl ON f.id = fl.film_id
WHERE fd.director_id = ?
GROUP BY f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id
ORDER BY COUNT(fl.user_id) DESC, f.id