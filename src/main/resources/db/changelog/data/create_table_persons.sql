CREATE TABLE persons
(
    id         BIGINT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    CONSTRAINT fk_person_user FOREIGN KEY (id) REFERENCES users (id)
);