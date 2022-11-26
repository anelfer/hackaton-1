CREATE TABLE IF NOT EXISTS habr
(
    id        SERIAL PRIMARY KEY,
    tag       VARCHAR(255) NOT NULL,
    title     VARCHAR(255) NOT NULL,
    text      TEXT         NOT NULL,
    url       VARCHAR(255) NOT NULL,
    time      VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);