SELECT r.id,
       r.content,
       r.is_positive,
       r.user_id,
       r.film_id,
       COALESCE(SUM(CASE WHEN rr.is_like IS NULL THEN 0 WHEN rr.is_like THEN 1 ELSE -1 END), 0) AS useful
FROM reviews r
LEFT JOIN review_reactions rr ON r.id = rr.review_id
WHERE r.film_id = ?
GROUP BY r.id, r.content, r.is_positive, r.user_id, r.film_id
ORDER BY useful DESC, r.id
LIMIT ?;