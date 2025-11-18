CREATE TABLE USERS (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    needs_relax_notification BOOLEAN NOT NULL DEFAULT FALSE
);
