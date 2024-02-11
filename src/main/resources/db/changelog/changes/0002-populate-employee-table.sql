-- changeset dzakirin:0002
INSERT INTO employee (id, first_name, last_name, email) VALUES
(gen_random_uuid(), 'John', 'Doe', 'john.doe@example.com'),
(gen_random_uuid(), 'Jane', 'Doe', 'jane.doe@example.com'),
(gen_random_uuid(), 'Alice', 'Johnson', 'alice.johnson@example.com'),
(gen_random_uuid(), 'Bob', 'Smith', 'bob.smith@example.com');

