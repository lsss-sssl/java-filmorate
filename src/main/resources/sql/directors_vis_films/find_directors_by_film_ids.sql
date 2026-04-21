SELECT fd.film_id,
       d.id   AS director_id,
       d.name AS director_name
FROM films_directors fd
         JOIN directors d ON fd.director_id = d.id
WHERE fd.film_id IN (:filmIds)
ORDER BY fd.film_id, d.id