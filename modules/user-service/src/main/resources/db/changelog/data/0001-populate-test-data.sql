-- changeset dzakirin:0001-populate-user-data

-- 1. Admin Profile (Matches Admin in Identity Service)
INSERT INTO user_profiles (id, first_name, last_name, phone_number, address) VALUES
    ('f0480d03-860c-4e97-8c9f-1fd1171f97b2', 'System', 'Administrator', '012-3456789', 'Template BE HQ, Malaysia');

-- 2. Seller Profile (Matches Seller in Identity Service)
INSERT INTO user_profiles (id, first_name, last_name, phone_number, address) VALUES
    ('bcd2d48e-7e73-4670-ab2c-43657b639c73', 'John', 'Doe', '019-8765432', 'Subang Jaya, Selangor');

-- 3. Buyer Profile (Matches Buyer in Identity Service)
INSERT INTO user_profiles (id, first_name, last_name, phone_number, address) VALUES
    ('32be2d1b-7e9e-4df7-89a0-ac13f547c606', 'Jane', 'Smith', '013-5551234', 'Kuala Lumpur, Malaysia');