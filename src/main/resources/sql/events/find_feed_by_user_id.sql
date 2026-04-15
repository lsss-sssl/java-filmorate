SELECT id,
       timestamp,
       user_id,
       event_type,
       operation,
       entity_id
FROM events
WHERE user_id = ?
ORDER BY id DESC;