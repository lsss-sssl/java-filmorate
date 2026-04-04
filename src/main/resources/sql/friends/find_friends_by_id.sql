SELECT u.*
FROM users u
         JOIN friendships f ON u.id = f.friend_id
WHERE f.user_id = ?