# java-filmorate

## ER-диаграмма
![image alt](https://github.com/lsss-sssl/java-filmorate/blob/add-database/ER.png?raw=true)

## Примеры запросов:
### Получить всех пользователей.
```sql
SELECT *
FROM users;
```
### Получить пользователя по id.
```sql
SELECT *
FROM users
WHERE id = 1;
```
### Получить список друзей пользователя.
```sql
SELECT u.*
FROM users u
JOIN friendships f ON u.id = f.friend_id
WHERE f.user_id = 1;
```
### Получить общих друзей.
```sql
SELECT u.*
FROM users u
JOIN friendships f1 ON u.id = f1.friend_id
JOIN friendships f2 ON u.id = f2.friend_id
WHERE f1.user_id = 1
AND f2.user_id = 2;
```
### Получить все фильмы.
```sql
SELECT *
FROM films;
```
### Получить фильм по id.
```sql
SELECT *
FROM films
WHERE id = 1;
```
### Получить популярные фильмы.
```sql
SELECT f.*,
       COUNT(fl.user_id) AS likes
FROM films f
LEFT JOIN film_likes fl ON f.id = fl.film_id
GROUP BY f.id
ORDER BY likes DESC
LIMIT 10;
```
