SELECT is_like
FROM review_reactions
WHERE review_id = ?
  AND user_id = ?;