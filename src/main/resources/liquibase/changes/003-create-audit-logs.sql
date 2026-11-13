-- liquibase formatted sql

-- changeset farida:3
CREATE TABLE audit_logs (
                            id BIGSERIAL PRIMARY KEY,
                            message VARCHAR(500),
                            created_at TIMESTAMP
);

-- rollback DROP TABLE audit_logs;