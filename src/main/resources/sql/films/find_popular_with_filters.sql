SELECT f.*, COUNT(l.user_id) as likes_count
FROM films f
LEFT JOIN film_likes l ON f.id = l.film_id
LEFT JOIN film_genres fg ON f.id = fg.film_id
WHERE 1=1
AND (fg.genre_id = ? OR ? IS NULL)
AND (EXTRACT(YEAR FROM f.release_date) = ? OR ? IS NULL)
GROUP BY f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id
ORDER BY likes_count DESC, f.id
LIMIT ?