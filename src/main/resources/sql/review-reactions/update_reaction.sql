UPDATE review_reactions
SET is_like = ?
WHERE review_id = ?
  AND user_id = ?