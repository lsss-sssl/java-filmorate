DELETE FROM review_reactions
WHERE review_id = ?
  AND user_id = ?
  AND is_like = ?