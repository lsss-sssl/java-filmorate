WITH similar AS (
    SELECT fl2.user_id
    FROM film_likes fl1
    JOIN film_likes fl2 ON fl1.film_id = fl2.film_id
    WHERE fl1.user_id = ?
      AND fl2.user_id <> ?
    GROUP BY fl2.user_id
    ORDER BY COUNT(*) DESC
    LIMIT 1
)
SELECT f.*
FROM films f
JOIN film_likes fl ON f.id = fl.film_id
WHERE fl.user_id = (SELECT user_id FROM similar)
  AND f.id NOT IN (SELECT film_id FROM film_likes WHERE user_id = ?)
ORDER BY f.id;