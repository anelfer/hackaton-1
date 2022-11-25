CREATE TABLE IF NOT EXISTS stackoverflow
(
    id        SERIAL PRIMARY KEY,
    tag       VARCHAR(255) NOT NULL,
    total     INT,
    week      INT,
    today     INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);