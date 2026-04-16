UPDATE reviews
SET content = ?,
    is_positive = ?,
    user_id = ?,
    film_id = ?
WHERE id = ?