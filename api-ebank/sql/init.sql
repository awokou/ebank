CREATE TABLE users (
    id SERIAL PRIMARY KEY NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    is_enabled BOOLEAN DEFAULT TRUE
);
CREATE TABLE history (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    user_id INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_history_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);
--------------- email: kodjo@gmail.com && password: password -------------------
INSERT INTO users ( created_at, name, email, password, role,is_enabled)
VALUES (NOW(), 'Kodzo Johnson', 'kodjo@gmail.com', '$2a$10$oxrtnH9.LBfwooxS.DaUUeZVJxP67Qvi04b9z0oljntrkpcMxkiPq', 'ADMIN', true);