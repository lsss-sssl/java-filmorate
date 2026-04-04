SELECT status_id
FROM friendships
WHERE user_id = ?
  AND friend_id = ?;