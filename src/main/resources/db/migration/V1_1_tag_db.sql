CREATE TABLE IF NOT EXISTS tags
(
    id        SERIAL PRIMARY KEY,
    tag       VARCHAR(255) UNIQUE NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);