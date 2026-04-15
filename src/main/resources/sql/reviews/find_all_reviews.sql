SELECT r.*,
       COALESCE(SUM(CASE
                        WHEN rr.is_like = TRUE THEN 1
                        WHEN rr.is_like = FALSE THEN -1
                        ELSE 0
           END), 0) AS useful
FROM reviews r
         LEFT JOIN review_reactions rr ON r.id = rr.review_id
GROUP BY r.id, r.content, r.is_positive, r.user_id, r.film_id
ORDER BY useful DESC, r.id
LIMIT ?