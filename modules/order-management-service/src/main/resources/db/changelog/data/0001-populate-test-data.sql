-- changeset dzakirin:0001-populate-test-data.sql

-- Insert Sample Products
INSERT INTO products (id, title, price, stock) VALUES
    (gen_random_uuid(), 'Java Programming Book', 20.01, 100),
    (gen_random_uuid(), 'Spring Boot Essentials', 120.01, 50);

-- Insert Sample Orders
INSERT INTO orders (id, customer_id, order_date) VALUES
    (gen_random_uuid(), '32be2d1b-7e9e-4df7-89a0-ac13f547c606', NOW()),
    (gen_random_uuid(), '32be2d1b-7e9e-4df7-89a0-ac13f547c606', NOW());

-- Insert Sample Order-Products (Many-to-Many)
INSERT INTO order_products (id, order_id, product_id, quantity) VALUES
    (gen_random_uuid(),
     (SELECT id FROM orders WHERE customer_id = '32be2d1b-7e9e-4df7-89a0-ac13f547c606' LIMIT 1),
     (SELECT id FROM products WHERE title = 'Java Programming Book'),
     2
    ),
    (gen_random_uuid(),
     (SELECT id FROM orders WHERE customer_id = '32be2d1b-7e9e-4df7-89a0-ac13f547c606' LIMIT 1),
     (SELECT id FROM products WHERE title = 'Spring Boot Essentials'),
     1
    );
