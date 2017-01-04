DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100000, '2017-01-01 07:42:00.000000', 'Завтрак', 510),
  (100000, '2017-01-01 13:15:00.000000', 'Обед', 1010),
  (100000, '2017-01-01 19:00:00.000000', 'Ужин', 300),
  (100000, '2017-01-02 06:00:00.000000', 'Завтрак', 700),
  (100000, '2017-01-02 14:00:00.000000', 'Обед', 923),
  (100000, '2017-01-02 22:00:00.000000', 'Ужин', 513),
  (100001, '2017-01-01 13:32:00.000000', 'Завтрак', 321),
  (100001, '2017-01-01 15:43:00.000000', 'Обед', 598),
  (100001, '2017-01-01 21:00:00.000000', 'Ужин', 498),
  (100001, '2017-01-02 07:21:00.000000', 'Завтрак', 110),
  (100001, '2017-01-02 15:32:00.000000', 'Обед', 1310),
  (100001, '2017-01-02 19:15:00.000000', 'Ужин', 710);

