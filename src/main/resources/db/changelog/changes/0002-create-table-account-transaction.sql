-- Changeset dzakirin:0003
CREATE TABLE account (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    account_number VARCHAR(255) NOT NULL UNIQUE,
    balance NUMERIC(19, 4) NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE transaction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID NOT NULL,
    amount NUMERIC(19, 4) NOT NULL,
    transaction_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id)
);
