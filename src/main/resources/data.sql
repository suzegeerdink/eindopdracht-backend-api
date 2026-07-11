-- ============ USERS ============
INSERT INTO users (id, email, keycloak_id) VALUES
(1, 'tony@example.com', 'df83deaa-70b8-47de-ae60-99424f052819'),
(2, 'leia@example.com', '2b0e7fe2-d8d4-4ee3-8bbe-f3c6825305cc'),
(3, 'sherlock@example.com', 'e20f1cff-b712-4248-bf77-656d48b33310');

-- ============ GENRES ============
INSERT INTO genre (id, name) VALUES
(1, 'Action'),
(2, 'Sci-Fi'),
(3, 'Comedy'),
(4, 'Fantasy'),
(5, 'Drama'),
(6, 'Horror');

-- ============ CONTENT (parent table, JOINED inheritance) ============
INSERT INTO content (id, title, description, age_classification) VALUES
(1, 'The Matrix', 'Science fiction classic', 16),
(2, 'Breaking Bad', 'A chemistry teacher turns to crime.', 16),
(3, 'The Hangover', 'A bachelor party in Las Vegas goes very wrong.', 16);

-- ============ FILMS (subclass) ============
INSERT INTO films (id, duration) VALUES
(1, 136),
(3, 100);

-- ============ SERIES (subclass) ============
INSERT INTO series (id, number_of_episodes, number_of_seasons) VALUES
(2, 62, 5);

-- ============ CONTENT_GENRE (many-to-many join table) ============
INSERT INTO content_genre (content_id, genre_id) VALUES
(1, 2), -- Matrix: Sci-Fi
(1, 1), -- Matrix: Action
(2, 5), -- Breaking Bad: Drama
(3, 3); -- Hangover: Comedy

-- ============ PROFILE ============
INSERT INTO profile (id, user_id, display_name, birth_date) VALUES
(1, 1, 'tony', '1985-05-12'),
(2, 2, 'leia', '1990-03-20'),
(3, 3, 'sherlock', '1978-11-02'),
(4, 3, 'kids', '2015-07-08');

-- ============ PROFILE_SETTINGS (one-to-one) ============
INSERT INTO profile_settings (id, autoplay_enabled, language, profile_id) VALUES
(1, true, 'en', 1),
(2, false, 'nl', 2),
(3, true, 'en', 3),
(4, false, 'nl', 4);

-- ============ LOAN ============
INSERT INTO loan (id, profile_id, content_id, loaned_out) VALUES
(1, 1, 1, true),
(2, 2, 2, false),
(3, 4, 3, true);

-- ============ WATCH_HISTORY ============
INSERT INTO watch_history (id, profile_id, content_id, watch_date) VALUES
(1, 1, 1, CURRENT_DATE - INTERVAL '6 days'),
(2, 1, 2, CURRENT_DATE - INTERVAL '3 day'),
(3, 4, 3, CURRENT_DATE);

-- ============ SEQUENCES RESETTEN ============

SELECT setval(pg_get_serial_sequence('users', 'id'), (SELECT MAX(id) FROM users));
SELECT setval(pg_get_serial_sequence('genre', 'id'), (SELECT MAX(id) FROM genre));
SELECT setval(pg_get_serial_sequence('content', 'id'), (SELECT MAX(id) FROM content));
SELECT setval(pg_get_serial_sequence('profile', 'id'), (SELECT MAX(id) FROM profile));
SELECT setval(pg_get_serial_sequence('profile_settings', 'id'), (SELECT MAX(id) FROM profile_settings));
SELECT setval(pg_get_serial_sequence('loan', 'id'), (SELECT MAX(id) FROM loan));
SELECT setval(pg_get_serial_sequence('watch_history', 'id'), (SELECT MAX(id) FROM watch_history));