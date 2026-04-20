SELECT f.id,
       f.name,
       f.description,
       f.release_date,
       f.duration,
       f.mpa_id
FROM films f
JOIN directors_vis_films dvf ON dvf.film_id = f.id
LEFT JOIN film_likes fl ON fl.film_id = f.id
WHERE dvf.director_id = ?
GROUP BY f.id,
         f.name,
         f.description,
         f.release_date,
         f.duration,
         f.mpa_id
ORDER BY COUNT(fl.user_id) DESC;