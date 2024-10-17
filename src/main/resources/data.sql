-- Roller
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Kullanıcılar
INSERT INTO users (username, email, password) VALUES
('admin', 'admin@example.com', '$2a$10$Dow1m1L6N9I9tN4N3eLf7uZ3qK6Tzj0qJjKZfKqGXe8FVgxyGk8jW'); -- Şifre: admin123

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 2); -- admin kullanıcısına ROLE_ADMIN verildi
