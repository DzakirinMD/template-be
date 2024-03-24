-- Changeset dzakirin:0002
INSERT INTO "user" (id, first_name, last_name, email, address)
VALUES
    (gen_random_uuid(), 'John', 'Doe', 'john.doe@example.com', '{"postcode": "1234", "city": "Anytown", "date": "2022-03-30"}'),
    (gen_random_uuid(), 'Emily', 'Jones', 'emily.jones@example.com', '{"postcode": "1234", "city": "Anytown", "date": "2023-04-15"}'),
    (gen_random_uuid(), 'Jane', 'Smith', 'jane.smith@example.com', '{"postcode": "4567", "city": "Sometown", "date": "2021-10-10"}');

INSERT INTO account (id, user_id, account_number, balance)
VALUES
    (gen_random_uuid(), (SELECT id FROM "user" WHERE email = 'john.doe@example.com'), 'ACC00000001', 0),
    (gen_random_uuid(), (SELECT id FROM "user" WHERE email = 'emily.jones@example.com'), 'ACC00000002', 0);