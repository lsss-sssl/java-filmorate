SELECT d.id,
       d.name,
       dvf.film_id
FROM directors_vis_films dvf
JOIN directors d ON d.id = dvf.director_id
WHERE dvf.film_id IN (:filmIds)
ORDER BY d.id;