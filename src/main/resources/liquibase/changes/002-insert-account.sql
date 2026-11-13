-- liquibase formatted sql

-- changeset farida:2
INSERT INTO accounts (owner_name, balance) VALUES ('Fidan', 1000.0);
INSERT INTO accounts (owner_name, balance) VALUES ('Ali', 500.0);

-- rollback DELETE FROM accounts WHERE owner_name IN ('Mahammad', 'Ali');