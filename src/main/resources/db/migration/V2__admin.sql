INSERT INTO role(id, name)
VALUES ('81a71a22-0b06-11ec-9a03-0242ac130003','ROLE_ADMIN');

INSERT INTO users(id, name, password, role_id)
VALUES ('fc1a3f6a-0b05-11ec-9a03-0242ac130003', 'admin',
        '$2a$12$tulcQxVvi/ix95QQCLoH7eLE9SQmhPG5V28Iy0wb.KMCCjpEzS3T6',
        (SELECT id FROM role where name = 'ROLE_ADMIN'));