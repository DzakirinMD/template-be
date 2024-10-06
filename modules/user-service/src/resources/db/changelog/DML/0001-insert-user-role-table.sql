-- changeset dzakirin:0001-insert-user-role-table

-- Inserting users into the 'users' table
INSERT INTO "user" (id, email, password, username) VALUES
('7a02f322-14ff-4717-95b1-8ced9bfd0b92', 'user@example.com', 'password123', 'user'),
('51603047-c0b1-446b-8149-62d52ea39457', 'moderator@example.com', 'password123', 'moderator'),
('4faf2383-5a1b-48de-8765-d9e8b44dcec9', 'admin@example.com', 'password123', 'admin');

-- Inserting roles into the 'roles' table
INSERT INTO roles (id, role_name) VALUES
('d023b064-2a1f-47de-aa4d-c922f57b6d6f', 'USER'),
('e240a18d-bd54-4def-97a1-8e81a2db55ef', 'MODERATOR'),
('0a4da45c-c203-4050-a7e4-35c5f24893e5', 'ADMIN');

-- Assign 'USER' role to 'user'
INSERT INTO user_roles (user_id, role_id)
VALUES ('7a02f322-14ff-4717-95b1-8ced9bfd0b92', 'd023b064-2a1f-47de-aa4d-c922f57b6d6f');

-- Assign 'MODERATOR' role to 'moderator'
INSERT INTO user_roles (user_id, role_id)
VALUES ('51603047-c0b1-446b-8149-62d52ea39457', 'e240a18d-bd54-4def-97a1-8e81a2db55ef');

-- Assign 'ADMIN' role to 'admin'
INSERT INTO user_roles (user_id, role_id)
VALUES ('4faf2383-5a1b-48de-8765-d9e8b44dcec9', '0a4da45c-c203-4050-a7e4-35c5f24893e5');
