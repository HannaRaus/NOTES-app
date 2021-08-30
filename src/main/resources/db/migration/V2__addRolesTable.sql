CREATE TABLE role
(
    id   BINARY(16) PRIMARY KEY,
    name varchar(100) UNIQUE NOT NULL
);

ALTER TABLE users
    DROP role;

ALTER TABLE users
    ADD role_id BINARY(16) NOT NULL;

ALTER TABLE users
    ADD FOREIGN KEY (role_id) REFERENCES role (id);
