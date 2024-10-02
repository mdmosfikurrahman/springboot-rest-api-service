CREATE TABLE token_blacklist
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    token          VARCHAR(512) NOT NULL UNIQUE,
    user_id        BIGINT       NOT NULL,
    invalidated_at DATETIME     NOT NULL,
    expires_at     DATETIME     NOT NULL
);
