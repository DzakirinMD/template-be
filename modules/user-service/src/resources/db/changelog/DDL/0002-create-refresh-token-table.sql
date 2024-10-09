-- changeset dzakirin:0002-add-refresh-token-table

CREATE TABLE token (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    token VARCHAR(255) NOT NULL UNIQUE,
    token_type VARCHAR(50) NOT NULL DEFAULT 'BEARER',
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    user_id UUID,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);