MERGE INTO friendships (user_id, friend_id, status_id)
    KEY (user_id, friend_id) VALUES (?, ?, ?)