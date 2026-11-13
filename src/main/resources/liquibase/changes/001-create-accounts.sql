-- liquibase formatted sql

-- changeset farida:1
CREATE TABLE accounts (
                          id BIGSERIAL PRIMARY KEY,
                          owner_name VARCHAR(255) NOT NULL,
                          balance DOUBLE PRECISION NOT NULL
);
-- rollback DROP TABLE accoun