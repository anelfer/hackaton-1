CREATE TABLE IF NOT EXISTS headhunter
(
    id        SERIAL PRIMARY KEY,
    tag       VARCHAR(255) NOT NULL,
    name      VARCHAR(255) NOT NULL,
    price     INT,
    currency  VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);