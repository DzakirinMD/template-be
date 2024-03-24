-- Changeset dzakirin:0002
INSERT INTO "user" (id, first_name, last_name, email, address)
VALUES
    (gen_random_uuid(), 'John', 'Doe', 'john.doe@example.com', '{"postcode": "1234", "city": "Anytown", "date": "2022-03-30"}'),
    (gen_random_uuid(), 'Emily', 'Jones', 'emily.jones@example.com', '{"postcode": "1234", "city": "Anytown", "date": "2023-04-15"}'),
    (gen_random_uuid(), 'Jane', 'Smith', 'jane.smith@example.com', '{"postcode": "4567", "city": "Sometown", "date": "2021-10-10"}'),
    (gen_random_uuid(), 'Mike', 'Brown', 'mike.brown@example.com', '{"postcode": "4567", "city": "Sometown", "date": "2020-12-05"}'),
    (gen_random_uuid(), 'Anna', 'Taylor', 'anna.taylor@example.com', '{"postcode": "7890", "city": "New City", "date": "2024-01-20"}'),
    (gen_random_uuid(), 'David', 'Wilson', 'david.wilson@example.com', '{"postcode": "7890", "city": "New City", "date": "2023-08-08"}');
