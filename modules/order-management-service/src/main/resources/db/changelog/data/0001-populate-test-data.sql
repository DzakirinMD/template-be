-- changeset dzakirin:0001-populate-test-data.sql

INSERT INTO products (id, title, price, stock) VALUES
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Java Programming Book', 20.01, 100),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'Spring Boot Essentials', 120.01, 50);

-- Order 1: PENDING (Buying 2 Java Books)
INSERT INTO orders (id, customer_id, total_amount, status, order_date) VALUES
    ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b11', '32be2d1b-7e9e-4df7-89a0-ac13f547c606', 40.02, 'PENDING', NOW());

-- Order 2: COMPLETED (Buying 1 Spring Boot Book)
INSERT INTO orders (id, customer_id, total_amount, status, order_date) VALUES
    ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b22', '32be2d1b-7e9e-4df7-89a0-ac13f547c606', 120.01, 'COMPLETED', NOW());

-- Order 3: CANCELLED (Buying 1 Java Book + 1 Spring Boot Book)
INSERT INTO orders (id, customer_id, total_amount, status, order_date) VALUES
    ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b33', '32be2d1b-7e9e-4df7-89a0-ac13f547c606', 140.02, 'CANCELLED', NOW());

INSERT INTO order_items (id, order_id, product_id, price_at_purchase, quantity) VALUES
    -- Items for Order 1 (2x Java Books)
    (gen_random_uuid(), 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b11', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 20.01, 2),

    -- Items for Order 2 (1x Spring Boot)
    (gen_random_uuid(), 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b22', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 120.01, 1),

    -- Items for Order 3 (1x Java Book, 1x Spring Boot)
    (gen_random_uuid(), 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b33', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 20.01, 1),
    (gen_random_uuid(), 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b33', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 120.01, 1);