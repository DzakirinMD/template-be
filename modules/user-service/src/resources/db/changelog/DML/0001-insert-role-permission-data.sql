-- changeset dzakirin:0001-insert-user-role-table

INSERT INTO role (id, role_name)
VALUES ('1755ca08-6e87-48a6-93b7-50e8a4841963', 'USER'),
       ('a2a2a2c1-cc75-47a0-bfba-75e19cbd525d', 'ADMIN'),
       ('65ef853e-9b4c-4a56-a49c-28a04cd7142f', 'MANAGER');

INSERT INTO permissions (id, permission_name)
VALUES ('76351b56-e00e-4e5c-91e3-3161689ea350', 'admin.read'),
       ('f810d359-cea3-47d0-afde-61340d0d6ce5', 'admin.update'),
       ('ede9b2c3-1b90-4217-91e2-eb1bfcd1269e', 'admin.create'),
       ('3f5f1ae4-ded8-4b30-83d6-1aecfbf79223', 'admin.delete'),
       ('ef38444b-0dcc-4939-a1a5-ee802dea3bf1', 'management.read'),
       ('cf513b37-09dd-489c-a074-b50964445581', 'management.update'),
       ('602e3a04-c533-4333-87e5-a7f4d485b0c4', 'management.create'),
       ('51fe4af1-fd44-421b-ba64-977a4e6e2f1b', 'management.delete'),
       ('5f984a30-273d-4402-9aea-f5e2a96297c5', 'user.read');

-- ADMIN permission
INSERT INTO role_permission (role_id, permission_id)
VALUES ('a2a2a2c1-cc75-47a0-bfba-75e19cbd525d', '76351b56-e00e-4e5c-91e3-3161689ea350'), -- admin.read
       ('a2a2a2c1-cc75-47a0-bfba-75e19cbd525d', 'f810d359-cea3-47d0-afde-61340d0d6ce5'), -- admin.update
       ('a2a2a2c1-cc75-47a0-bfba-75e19cbd525d', 'ede9b2c3-1b90-4217-91e2-eb1bfcd1269e'), -- admin.create
       ('a2a2a2c1-cc75-47a0-bfba-75e19cbd525d', '3f5f1ae4-ded8-4b30-83d6-1aecfbf79223'), -- admin.delete
       ('a2a2a2c1-cc75-47a0-bfba-75e19cbd525d', 'ef38444b-0dcc-4939-a1a5-ee802dea3bf1'), -- management.read
       ('a2a2a2c1-cc75-47a0-bfba-75e19cbd525d', 'cf513b37-09dd-489c-a074-b50964445581'), -- management.update
       ('a2a2a2c1-cc75-47a0-bfba-75e19cbd525d', '602e3a04-c533-4333-87e5-a7f4d485b0c4'), -- management.create
       ('a2a2a2c1-cc75-47a0-bfba-75e19cbd525d', '51fe4af1-fd44-421b-ba64-977a4e6e2f1b'); -- management.delete

-- MANAGER permission
INSERT INTO role_permission (role_id, permission_id)
VALUES ('65ef853e-9b4c-4a56-a49c-28a04cd7142f', 'ef38444b-0dcc-4939-a1a5-ee802dea3bf1'), -- management.read
       ('65ef853e-9b4c-4a56-a49c-28a04cd7142f', 'cf513b37-09dd-489c-a074-b50964445581'), -- management.update
       ('65ef853e-9b4c-4a56-a49c-28a04cd7142f', '602e3a04-c533-4333-87e5-a7f4d485b0c4'), -- management.create
       ('65ef853e-9b4c-4a56-a49c-28a04cd7142f', '51fe4af1-fd44-421b-ba64-977a4e6e2f1b'); -- management.delete

-- USER permission
INSERT INTO role_permission (role_id, permission_id)
VALUES ('1755ca08-6e87-48a6-93b7-50e8a4841963', '5f984a30-273d-4402-9aea-f5e2a96297c5'); -- user.read