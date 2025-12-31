-- changeset dzakirin:0001-populate-identity-data

INSERT INTO roles (id, name) VALUES
    (1, 'CUSTOMER'),
    (2, 'SELLER'),
    (3, 'ADMIN');

-- Insert Users (Password is 'password')
INSERT INTO user_credentials (id, email, password, enabled) VALUES
-- hard code initial user credential id for easy reference
    ('f0480d03-860c-4e97-8c9f-1fd1171f97b2', 'admin@example.com', '$2a$10$LQmtSNPWkA5w0bMp3ClMKe5qhqO.kNGPKVs.bel9ilkUfkgMTChE.', true),
    ('bcd2d48e-7e73-4670-ab2c-43657b639c73', 'seller@example.com', '$2a$10$LQmtSNPWkA5w0bMp3ClMKe5qhqO.kNGPKVs.bel9ilkUfkgMTChE.', true),
    ('32be2d1b-7e9e-4df7-89a0-ac13f547c606', 'buyer@example.com', '$2a$10$LQmtSNPWkA5w0bMp3ClMKe5qhqO.kNGPKVs.bel9ilkUfkgMTChE.', true);

INSERT INTO user_roles (user_id, role_id) VALUES
    -- Admin User -> Has ROLE_ADMIN (3), SELLER (2), CUSTOMER (1)
    ('f0480d03-860c-4e97-8c9f-1fd1171f97b2', 3),
    ('f0480d03-860c-4e97-8c9f-1fd1171f97b2', 2),
    ('f0480d03-860c-4e97-8c9f-1fd1171f97b2', 1),

    -- Seller User -> Has ROLE_SELLER (2), CUSTOMER (1)
    ('bcd2d48e-7e73-4670-ab2c-43657b639c73', 2),
    ('bcd2d48e-7e73-4670-ab2c-43657b639c73', 1),

    -- Buyer User -> Has ROLE_CUSTOMER (1)
    ('32be2d1b-7e9e-4df7-89a0-ac13f547c606', 1);