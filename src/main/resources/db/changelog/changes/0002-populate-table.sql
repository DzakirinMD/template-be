-- changeset dzakirin:0002
-- Insert departments
INSERT INTO department (id, name, description)
VALUES
    (gen_random_uuid(), 'HR', 'Human Resources Department'),
    (gen_random_uuid(), 'IT', 'Information Technology Department'),
    (gen_random_uuid(), 'Finance', 'Finance and Accounting Department');

-- Insert employees for HR
INSERT INTO employee (id, first_name, last_name, email, department_id, address)
VALUES
    (gen_random_uuid(), 'John', 'Doe', 'john.doe@example.com', (SELECT id FROM department WHERE name = 'HR'), '{"postcode": "1234", "city": "Anytown", "date": "2022-03-30"}'),
    (gen_random_uuid(), 'Emily', 'Jones', 'emily.jones@example.com', (SELECT id FROM department WHERE name = 'HR'), '{"postcode": "1234", "city": "Anytown", "date": "2023-04-15"}');

-- Insert employees for IT
INSERT INTO employee (id, first_name, last_name, email, department_id, address)
VALUES
    (gen_random_uuid(), 'Jane', 'Smith', 'jane.smith@example.com', (SELECT id FROM department WHERE name = 'IT'), '{"postcode": "4567", "city": "Sometown", "date": "2021-10-10"}'),
    (gen_random_uuid(), 'Mike', 'Brown', 'mike.brown@example.com', (SELECT id FROM department WHERE name = 'IT'), '{"postcode": "4567", "city": "Sometown", "date": "2020-12-05"}');

-- Insert employees for Finance
INSERT INTO employee (id, first_name, last_name, email, department_id, address)
VALUES
    (gen_random_uuid(), 'Anna', 'Taylor', 'anna.taylor@example.com', (SELECT id FROM department WHERE name = 'Finance'), '{"postcode": "7890", "city": "New City", "date": "2024-01-20"}'),
    (gen_random_uuid(), 'David', 'Wilson', 'david.wilson@example.com', (SELECT id FROM department WHERE name = 'Finance'), '{"postcode": "7890", "city": "New City", "date": "2023-08-08"}');
