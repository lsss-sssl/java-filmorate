SELECT f.*
FROM films f
JOIN film_likes fl1 ON f.id = fl1.film_id
JOIN film_likes fl2 ON f.id = fl2.film_id
WHERE fl1.user_id = ?
   AND fl2.user_id = ?
GROUP BY f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id
ORDER BY COUNT(*) DESC, f.id;