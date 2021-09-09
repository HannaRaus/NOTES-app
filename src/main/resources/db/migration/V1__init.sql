CREATE TABLE role
(
    id   VARCHAR(255) PRIMARY KEY,
    name varchar(100) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id       VARCHAR(255) PRIMARY KEY,
    name     varchar(100) UNIQUE NOT NULL,
    password varchar(200)        NOT NULL,
    role_id  VARCHAR(255),
    FOREIGN KEY (role_id) REFERENCES role (id)
        ON DELETE SET null
);

CREATE TABLE notes
(
    id          VARCHAR(255) PRIMARY KEY,
    title       varchar(200)   NOT NULL,
    content     varchar(10000) NOT NULL,
    access_type varchar(100)   NOT NULL,
    user_id     VARCHAR(255)   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE
);
