SELECT u.*
FROM users u
         JOIN friendships f1 ON u.id = f1.friend_id
         JOIN friendships f2 ON u.id = f2.friend_id
WHERE f1.user_id = ?
  AND f2.user_id = ?;