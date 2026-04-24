SELECT DISTINCT f.*, COUNT(l.user_id) as likes_count
FROM films f
LEFT JOIN film_likes l ON f.id = l.film_id
LEFT JOIN films_directors fd ON f.id = fd.film_id
LEFT JOIN directors d ON fd.director_id = d.id
WHERE 1=1
AND (
    (? = true AND LOWER(f.name) LIKE LOWER(CONCAT('%', ?, '%')))
    OR (? = true AND LOWER(d.name) LIKE LOWER(CONCAT('%', ?, '%')))
)
GROUP BY f.id
ORDER BY likes_count DESC, f.id