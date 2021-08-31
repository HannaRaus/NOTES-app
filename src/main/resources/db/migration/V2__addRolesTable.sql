CREATE TABLE role
(
    id   UUID PRIMARY KEY,
    name varchar(100) UNIQUE NOT NULL,
    user_id UUID
);

ALTER TABLE users
    DROP role;

ALTER TABLE users
    ADD role_id UUID NOT NULL;

ALTER TABLE users
    ADD FOREIGN KEY (role_id) REFERENCES role (id);

/*





CREATE TABLE role
(
    id   BINARY(16) PRIMARY KEY,
    name varchar(100) UNIQUE NOT NULL,
    user_id BINARY(16)
);

ALTER TABLE users
    DROP role;

ALTER TABLE users
    ADD role_id BINARY(16) NOT NULL;

ALTER TABLE users
    ADD FOREIGN KEY (role_id) REFERENCES role (id);
*/
